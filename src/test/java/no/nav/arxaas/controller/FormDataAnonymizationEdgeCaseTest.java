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

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FormDataAnonymizationEdgeCaseTest {

    @Autowired
    private MockMvc mvc;

    private MockMultipartFile file;
    private MockMultipartFile metadata;
    private MockMultipartFile genderHierarchy;
    private MockMultipartFile zipcodeHierarchy;


    @BeforeEach
    void setUp(){
        String testMetaData = GenerateIntegrationTestData.testFormData_metadata_two_quasi_with_hierarchies();

        file = (MockMultipartFile) GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
        metadata = new MockMultipartFile("metadata", "","application/json", testMetaData.getBytes());
        genderHierarchy = (MockMultipartFile) GenerateTestData.genderHierarchyMultipartFile();
        zipcodeHierarchy = (MockMultipartFile) GenerateTestData.zipcodeHierarchyMultipartFile();
    }

    @Test
    void formdata_anonymization_missing_data_should_return_bad_request() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(metadata);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_missing_attributes_should_return_bad_request() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_missing_hierarchies_should_return_bad_request() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metadata);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_null_hierarchies_should_return_request_if_able_to_anonymize() throws Exception {

        MockMultipartFile nullHierarchies = new MockMultipartFile("hierachies","","text/csv", InputStream.nullInputStream());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metadata)
                .file(nullHierarchies);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymize_null_payload() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file");
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_payload_containing_to_many_quasi_vs_hierarchies_should_return_bad_request() throws Exception {

        MockMultipartFile metaDataToManyQuasi = GenerateEdgeCaseData.testMetaDataThreeQuasi();
        MockMultipartFile nullHierarchies = new MockMultipartFile("hierachies", "", "text/csv", InputStream.nullInputStream());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataToManyQuasi)
                .file(nullHierarchies);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_missing_privacy_model_should_return_bad_request() throws Exception {
        MockMultipartFile metaDataToManyQuasi = GenerateEdgeCaseData.testMetaDataMissingPrivacyModel();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataToManyQuasi)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_data_format() throws Exception {
        MockMultipartFile testDatasetWrongFormat = GenerateEdgeCaseData.testDatasetComma();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(testDatasetWrongFormat)
                .file(metadata)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_hierarchy() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metadata)
                .file(zipcodeHierarchy)
                .file(genderHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_hierarchy_format() throws Exception {

        MockMultipartFile zipcodeHierarchyWrongFormat = GenerateEdgeCaseData.testZipcodeHierarchyWrongFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metadata)
                .file(genderHierarchy)
                .file(zipcodeHierarchyWrongFormat);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_hierarchy_having_data_not_existing_in_dataset() throws Exception {

        MockMultipartFile metaData1Sensitive = GenerateEdgeCaseData.testMetaDataOneSensitive();
        MockMultipartFile zipcodeHierarchyMoreData = GenerateEdgeCaseData.testZipcodeHierarchyMoreData();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaData1Sensitive)
                .file(zipcodeHierarchyMoreData);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_privacy_model_on_non_sensitive_data() throws Exception {

        MockMultipartFile metaDataPrivacyModel = GenerateEdgeCaseData.testMetaDataPrivacyModelOnNonExistingAttribute();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataPrivacyModel)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_privacy_model_format() throws Exception {
        MockMultipartFile metaDataWrongPrivacyFormat = GenerateEdgeCaseData.testMetaDataWrongPrivacyFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataWrongPrivacyFormat)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

    }

    @Test
    void formdata_anonymization_with_wrong_attribute_format() throws Exception {
        MockMultipartFile metaDataWrongAttributeFormat = GenerateEdgeCaseData.testMetaDataWrongFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataWrongAttributeFormat)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_format_all() throws Exception {
        MockMultipartFile testDatasetWrongFormat = GenerateEdgeCaseData.testDatasetComma();
        MockMultipartFile metaDataWrongAttributeFormat = GenerateEdgeCaseData.testMetaDataWrongFormat();
        MockMultipartFile zipcodeHierarchyWrongFormat = GenerateEdgeCaseData.testZipcodeHierarchyWrongFormat();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(testDatasetWrongFormat)
                .file(metaDataWrongAttributeFormat)
                .file(genderHierarchy)
                .file(zipcodeHierarchyWrongFormat);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_suppression_limit_greater_than_1() throws Exception {
        MockMultipartFile metaDataSuppressionHigherThan1 = GenerateEdgeCaseData.testMetaDataSuppressionHigherThanOne();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataSuppressionHigherThan1)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void formdata_anonymization_with_wrong_suppression_limit_lesser_than_0() throws Exception {
        MockMultipartFile metaDataSuppressionLowerThan0 = GenerateEdgeCaseData.testMetaDataSuppressionLowerThanZero();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(file)
                .file(metaDataSuppressionLowerThan0)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);
        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

}
