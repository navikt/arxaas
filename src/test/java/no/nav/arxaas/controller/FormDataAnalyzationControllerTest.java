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
    void setUp(){
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_two_quasi();

         file = (MockMultipartFile) GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
         metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
    }


    @Test
    void getPayloadFormDataAnalyze() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file").file(file).file(metadata);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reIdentificationRisk.measures.estimated_journalist_risk").value("1.0"))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].interval").value("[50,100]"))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].recordsWithRiskWithinInterval").value(1.0))
                .andExpect(jsonPath("$.distributionOfRisk.riskIntervalList[0].recordsWithRiskWithinInterval").value(1.0))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        RiskProfile actual = new ObjectMapper().readValue(jsonResult,RiskProfile.class);

        //check for re-identification risk values
        ReIdentificationRisk expectedReIdentificationRisk = GenerateTestData.ageGenderZipcodeReIndenticationRisk();
        Assertions.assertEquals(expectedReIdentificationRisk,actual.getReIdentificationRisk());

        //check for distribution of risk values
        DistributionOfRisk expectedDistributionOfRisk = GenerateTestData.ageGenderZipcodeDistributionOfRisk();
        Assertions.assertEquals(expectedDistributionOfRisk,actual.getDistributionOfRisk());

    }
}