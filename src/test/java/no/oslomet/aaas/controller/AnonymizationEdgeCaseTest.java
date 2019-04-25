package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateEdgeCaseData;
import no.oslomet.aaas.exception.ExceptionResponse;
import no.oslomet.aaas.model.anonymity.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnonymizationEdgeCaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Request missingDataPayload;
    private Request missingAttributesPayload;
    private Request missingPrivacyModelsPayload;
    private Request testRequestPayloadWithToManyQuasi;

    @BeforeEach
    void setUp() {
        missingDataPayload = GenerateEdgeCaseData.zipcodeRequestPayloadWithoutData();
        missingAttributesPayload = GenerateEdgeCaseData.zipcodeRequestPayloadWithoutAttributes();
        missingPrivacyModelsPayload = GenerateEdgeCaseData.zipcodeRequestPayloadWithoutPrivacyModels();
        testRequestPayloadWithToManyQuasi = GenerateEdgeCaseData.zipcodeRequestPayload3QuasiNoHierarchies();
    }

    @Test
    void anonymization_missing_data_should_return_bad_request() {
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",missingDataPayload, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }


    @Test
    void anonymization_missing_attributes_should_return_bad_request() {
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",missingAttributesPayload, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymize_null_payload(){
        Request nullPayload = GenerateEdgeCaseData.NullPayload();
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",nullPayload, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_payload_containing_to_many_quasi_vs_hierarchies_should_return_bad_request() {
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",testRequestPayloadWithToManyQuasi, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_missing_privacy_model_should_return_bad_request(){
        ResponseEntity<RuntimeException> responseEntity = restTemplate.postForEntity("/api/anonymize",missingPrivacyModelsPayload, RuntimeException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_data_format(){
        Request wrongDataFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_data_format();
        ResponseEntity<ArrayIndexOutOfBoundsException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongDataFormat, ArrayIndexOutOfBoundsException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_hierarchy(){
        Request wrongHierarchy = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_hierarchy();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongHierarchy, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_hierarchy_format(){
        Request wrongHierarchyFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_hierarchy_format();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongHierarchyFormat, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_hierarchy_having_data_not_existing_in_dataset(){
        Request wrongHierarchyFormat = GenerateEdgeCaseData.zipcodeRequestPaylaod_hierarchy_having_data_not_included_in_dataset();
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongHierarchyFormat, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        AnonymizationResultPayload actual = responseEntity.getBody();
        List<String[]> expected = GenerateEdgeCaseData.ageGenderZipcodeDataAfterAnonymization();
        assertNotNull(actual);
        for(int x = 0; x<1;x++) {
            assertArrayEquals(expected.get(x), actual.getAnonymizeResult().getData().get(x));
        }
    }

    @Test
    void anonymization_with_privacy_model_on_non_sensitive_data(){
        Request wrongPrivacyModel = GenerateEdgeCaseData.zipcodeRequestPayload_privacy_model_on_non_sensitive_data();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongPrivacyModel, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_privacy_model_format(){
        Request wrongPrivacyModelFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_privacy_model_format();
        ResponseEntity<NumberFormatException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongPrivacyModelFormat, NumberFormatException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_attribute_format(){
        Request wrongAttributeFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_attribute_format();
        ResponseEntity<IndexOutOfBoundsException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongAttributeFormat, IndexOutOfBoundsException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_format_all(){
        Request wrongFormat = GenerateEdgeCaseData.zipcodeRequestPayload_all_format_wrong();
        ResponseEntity<NumberFormatException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongFormat, NumberFormatException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_suppression_limit_greater_than_1(){
        Request wrongFormat = GenerateEdgeCaseData.zipcodeRequestPayloadWithSuppressionLimitGreaterThan1();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongFormat, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void anonymization_with_wrong_suppression_limit_lesser_than_0(){
        Request wrongFormat = GenerateEdgeCaseData.zipcodeRequestPayloadWithSuppressionLimitLessserThan0();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/anonymize",wrongFormat, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }
}
