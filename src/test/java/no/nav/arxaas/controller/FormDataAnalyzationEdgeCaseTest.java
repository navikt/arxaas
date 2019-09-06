package no.nav.arxaas.controller;

import no.nav.arxaas.GenerateEdgeCaseData;
import no.nav.arxaas.GenerateIntegrationTestData;
import no.nav.arxaas.GenerateTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FormDataAnalyzationEdgeCaseTest {

    @Autowired
    private MockMvc mvc;

    private MockMultipartFile file;
    private MockMultipartFile metadata;


    @BeforeEach
    void setUp(){
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_2quasi();

        file = (MockMultipartFile) GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
        metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
    }

    @Test
    void getPayloadFormDataAnalyze_missing_data() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(metadata);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void getPayloadFormDataAnalyze_wrong_data_format() throws Exception {
        MockMultipartFile testDatasetWrongFormat = GenerateEdgeCaseData.testDatasetComma();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(testDatasetWrongFormat)
                .file(metadata);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void getPayloadFormDataAnalyze_missing_attribute() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(file);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void getPayloadFormDataAnalyze_wrong_attribute_format() throws Exception {

        MockMultipartFile testMetaDataWrongFormat = GenerateEdgeCaseData.testMetaDataWrongFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(file)
                .file(testMetaDataWrongFormat);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void getPayloadFormDataAnalyze_null_payload() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file");
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void getPayloadFormDataAnalyze_all_format_wrong() throws Exception {
        MockMultipartFile testDatasetWrongFormat = GenerateEdgeCaseData.testDatasetComma();
        MockMultipartFile testMetaDataWrongFormat = GenerateEdgeCaseData.testMetaDataWrongFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(testDatasetWrongFormat)
                .file(testMetaDataWrongFormat);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }
}
