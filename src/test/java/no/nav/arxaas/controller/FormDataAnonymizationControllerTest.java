package no.nav.arxaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.GenerateIntegrationTestData;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.anonymity.AnonymizationMetrics;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FormDataAnonymizationControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockMultipartFile file;
    private MockMultipartFile metadata;
    private MockMultipartFile genderHierarchy;
    private MockMultipartFile zipcodeHierarchy;

    @BeforeEach
    void setUp(){
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_two_quasi();

        file = (MockMultipartFile) GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
        metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
        genderHierarchy = (MockMultipartFile) GenerateTestData.genderHierarchyMultipartFile();
        zipcodeHierarchy = (MockMultipartFile) GenerateTestData.zipcodeHierarchyMultipartFile();
    }

    @Test
    void formdata_anonymization_post() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actual = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        assertNotNull(actual.getRiskProfile().getReIdentificationRisk().getMeasures().get("records_affected_by_highest_prosecutor_risk"));
        assertNotNull(actual.getAnonymizeResult().getData());

        //check for dataset values
        List<String[]> actualDataset = actual.getAnonymizeResult().getData();
        List<String[]> expectedDataset = GenerateTestData.ageGenderZipcodeDataAfterAnonymization();
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expectedDataset.get(x), actualDataset.get(x));
        }

        //check for attributes values
        List<Attribute> actualAttributes = actual.getAnonymizeResult().getAttributes();
        List<Attribute> expectedAttributes = GenerateTestData.zipcodeRequestPayload2Quasi().getAttributes();

        for(int x =0;x<3;x++){
            assertEquals(expectedAttributes.get(x).getField(),actualAttributes.get(x).getField());
            assertEquals(expectedAttributes.get(x).getAttributeTypeModel(),actualAttributes.get(x).getAttributeTypeModel());
        }

        //check for anonymization status values
        String actualStatus = actual.getAnonymizeResult().getAnonymizationStatus();
        assertEquals("ANONYMOUS",actualStatus);

        //check for metric values
        AnonymizationMetrics actualMetrics = actual.getAnonymizeResult().getMetrics();
        assertNotNull(actualMetrics.getProcessTimeMillisecounds());
        assertEquals(1, actualMetrics.getPrivacyModels().size());

        assertEquals(2, actualMetrics.getAttributeGeneralization().size());
        assertEquals(0, actualMetrics.getAttributeGeneralization().get(0).getGeneralizationLevel());
        assertEquals("gender", actualMetrics.getAttributeGeneralization().get(0).getName());
        assertEquals("QUASI_IDENTIFYING_ATTRIBUTE", actualMetrics.getAttributeGeneralization().get(0).getType());

    }
}