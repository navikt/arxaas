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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static no.nav.arxaas.model.AttributeTypeModel.IDENTIFYING;
import static no.nav.arxaas.model.AttributeTypeModel.QUASIIDENTIFYING;
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
    void setUp() throws IOException {
        MultipartFile testFile = GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_2quasi();
        MultipartFile testGenderHierarchy = GenerateTestData.genderHierarchyMultipartFile();
        MultipartFile testZipcodeHierarchy = GenerateTestData.zipcodeHierarchyMultipartFile();

        file = new MockMultipartFile("file", testFile.getOriginalFilename(),"text/csv", testFile.getBytes());
        metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
        genderHierarchy = new MockMultipartFile("hierarchies", testGenderHierarchy.getOriginalFilename(), "text/csv",testGenderHierarchy.getBytes());
        zipcodeHierarchy = new MockMultipartFile("hierarchies",testZipcodeHierarchy.getOriginalFilename(),"text/csv",testZipcodeHierarchy.getBytes());
    }

    @Test
    void formdata_anonymization_post() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actual = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        assertNotNull(actual.getRiskProfile().getReIdentificationRisk().getMeasures().get("records_affected_by_highest_prosecutor_risk"));
        assertNotNull(actual.getAnonymizeResult().getData());
    }

    @Test
    void formdata_anonymization_check_for_dataset_values() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actualResult = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        List<String[]> actual = actualResult.getAnonymizeResult().getData();
        List<String[]> expected = GenerateTestData.ageGenderZipcodeDataAfterAnonymization();
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    void formdata_anonymization_check_for_attributes_values() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actualResult = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        List<Attribute> actual = actualResult.getAnonymizeResult().getAttributes();
        List<Attribute> expected = GenerateTestData.zipcodeRequestPayload2Quasi().getAttributes();


        for(int x =0;x<3;x++){
            assertEquals(expected.get(x).getField(),actual.get(x).getField());
            assertEquals(expected.get(x).getAttributeTypeModel(),actual.get(x).getAttributeTypeModel());
        }
    }

    @Test
    void formdata_anonymization_check_for_anonymization_status_values() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actualResult = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        String actual = actualResult.getAnonymizeResult().getAnonymizationStatus();
        assertEquals("ANONYMOUS",actual);
    }

    @Test
    void anonymization_check_for_metric_values() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file").file(file).file(metadata).file(genderHierarchy).file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        AnonymizationResultPayload actualResult = new ObjectMapper().readValue(jsonResult,AnonymizationResultPayload.class);

        AnonymizationMetrics actual = actualResult.getAnonymizeResult().getMetrics();
        assertNotNull(actual.getProcessTimeMillisecounds());
        assertEquals(1, actual.getPrivacyModels().size());

        assertEquals(2, actual.getAttributeGeneralization().size());
        assertEquals(0, actual.getAttributeGeneralization().get(0).getGeneralizationLevel());
        assertEquals("gender", actual.getAttributeGeneralization().get(0).getName());
        assertEquals("QUASI_IDENTIFYING_ATTRIBUTE", actual.getAttributeGeneralization().get(0).getType());
    }
}