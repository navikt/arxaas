package no.nav.arxaas.utils;

import no.nav.arxaas.GenerateEdgeCaseData;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.model.FormMetaDataRequest;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.risk.ReIdentificationRisk;
import org.bouncycastle.cert.ocsp.Req;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

class FormDataFactoryTest {

    private FormDataFactory formDataFactory;
    private MultipartFile testFile;
    private FormMetaDataRequest testPayload;
    private MultipartFile[] testHierarchies;


    @BeforeEach
    void generateTestData(){
        formDataFactory = new FormDataFactory();
        testFile = GenerateTestData.ageGenderZipcodeDatasetMultipartFile();
        testPayload = GenerateTestData.formDataTestMetaData();
        testHierarchies = GenerateTestData.testHierarchiesMultipartFile();
    }

    @Test
    void create_with_null_data(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> (new FormDataFactory()).createAnalyzationPayload(testFile,null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> (new FormDataFactory()).createAnalyzationPayload(null,testPayload));
    }

    @Test
    void createAnalyzationPayload() {
        Request actual = formDataFactory.createAnalyzationPayload(testFile,testPayload);
        Request expected = GenerateTestData.zipcodeRequestPayload2Quasi();

        //check dataset
        Assertions.assertArrayEquals(expected.getData().get(1), actual.getData().get(1));
        Assertions.assertArrayEquals(expected.getData().get(2), actual.getData().get(2));
        Assertions.assertArrayEquals(expected.getData().get(3), actual.getData().get(3));

        //check attributes
        Assertions.assertEquals(expected.getAttributes().get(0).getField(),actual.getAttributes().get(0).getField());
        Assertions.assertEquals(expected.getAttributes().get(1).getAttributeTypeModel(),actual.getAttributes().get(1).getAttributeTypeModel());

        //check privacy model
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getPrivacyModel(),actual.getPrivacyModels().get(0).getPrivacyModel());
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getParams(),actual.getPrivacyModels().get(0).getParams());

        //suppression limit
        Assertions.assertEquals(expected.getSuppressionLimit(),expected.getSuppressionLimit());
    }

    @Test
    void createAnonymizationPayload() {
        Request actual = formDataFactory.createAnonymizationPayload(testFile,testPayload,testHierarchies);
        Request expected = GenerateTestData.zipcodeRequestPayload2Quasi();

        //check dataset
        Assertions.assertArrayEquals(expected.getData().get(1), actual.getData().get(1));
        Assertions.assertArrayEquals(expected.getData().get(2), actual.getData().get(2));
        Assertions.assertArrayEquals(expected.getData().get(3), actual.getData().get(3));

        //check attributes
        Assertions.assertEquals(expected.getAttributes().get(0).getField(),actual.getAttributes().get(0).getField());
        Assertions.assertEquals(expected.getAttributes().get(1).getAttributeTypeModel(),actual.getAttributes().get(1).getAttributeTypeModel());
        Assertions.assertArrayEquals(expected.getAttributes().get(2).getHierarchy().get(1),actual.getAttributes().get(2).getHierarchy().get(1));

        //check privacy model
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getPrivacyModel(),actual.getPrivacyModels().get(0).getPrivacyModel());
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getParams(),actual.getPrivacyModels().get(0).getParams());

        //suppression limit
        Assertions.assertEquals(expected.getSuppressionLimit(),expected.getSuppressionLimit());
    }

    @Test
    void createAnonymizationPayload_with_no_hierarchies(){
        Request actual = formDataFactory.createAnonymizationPayload(testFile,testPayload, new MultipartFile[]{});
        Request expected = GenerateTestData.zipcodeRequestPayload2Quasi();

        //check dataset
        Assertions.assertArrayEquals(expected.getData().get(1), actual.getData().get(1));
        Assertions.assertArrayEquals(expected.getData().get(2), actual.getData().get(2));
        Assertions.assertArrayEquals(expected.getData().get(3), actual.getData().get(3));

        //check attributes
        Assertions.assertEquals(expected.getAttributes().get(0).getField(),actual.getAttributes().get(0).getField());
        Assertions.assertEquals(expected.getAttributes().get(1).getAttributeTypeModel(),actual.getAttributes().get(1).getAttributeTypeModel());
        Assertions.assertNull(actual.getAttributes().get(2).getHierarchy());

        //check privacy model
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getPrivacyModel(),actual.getPrivacyModels().get(0).getPrivacyModel());
        Assertions.assertEquals(expected.getPrivacyModels().get(0).getParams(),actual.getPrivacyModels().get(0).getParams());

        //suppression limit
        Assertions.assertEquals(expected.getSuppressionLimit(),expected.getSuppressionLimit());
    }

    @Test
    void create_payload_with_dataset_delimiter_as_comma(){
        MultipartFile testFileComma = GenerateEdgeCaseData.testDatasetComma();
        Request actual = formDataFactory.createAnalyzationPayload(testFileComma,testPayload);
        Request expected = GenerateTestData.zipcodeRequestPayload2Quasi();

        //check dataset
        Assertions.assertArrayEquals(expected.getData().get(1), actual.getData().get(1));
        Assertions.assertArrayEquals(expected.getData().get(2), actual.getData().get(2));
        Assertions.assertArrayEquals(expected.getData().get(3), actual.getData().get(3));
    }
}