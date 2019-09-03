package no.nav.arxaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.GenerateIntegrationTestData;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.model.risk.DistributionOfRisk;
import no.nav.arxaas.model.risk.ReIdentificationRisk;
import no.nav.arxaas.model.risk.RiskProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FormDataAnalyzationControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockMultipartFile file;
    private MockMultipartFile metadata;


    @BeforeEach
    void setUp() throws IOException {
        MultipartFile testFile = GenerateTestData.ageGenderZipcodeMultipartFile();
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_2quasi();

         file = new MockMultipartFile("file", testFile.getOriginalFilename(),"text/csv", testFile.getInputStream().readAllBytes());
         metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
    }


    @Test
    void getPayloadFormDataAnalyze() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file").file(file).file(metadata);
        mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reIdentificationRisk.measures.estimated_journalist_risk").value("1.0"))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].interval").value("[50,100]"))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].recordsWithRiskWithinInterval").value(1.0))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].recordsWithRiskWithinInterval").value(1.0));
    }

    @Test
    void getPayloadFormDataAnalyze__check_for_re_identification_risk_values() throws Exception {

        ReIdentificationRisk expected = GenerateTestData.ageGenderZipcodeReIndenticationRisk();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file").file(file).file(metadata);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        RiskProfile actual = new ObjectMapper().readValue(jsonResult,RiskProfile.class);
        Assertions.assertEquals(expected,actual.getReIdentificationRisk());
    }

    @Test
    void getPayloadFormDataAnalyze__check_for_distribution_of_risk_values() throws Exception{

        DistributionOfRisk expected = GenerateTestData.ageGenderZipcodeDistributionOfRisk();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file").file(file).file(metadata);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        RiskProfile actual = new ObjectMapper().readValue(jsonResult,RiskProfile.class);

        Assertions.assertEquals(expected,actual.getDistributionOfRisk());
    }
}