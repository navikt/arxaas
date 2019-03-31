package no.oslomet.aaas.controller;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.exception.ExceptionResponse;
import no.oslomet.aaas.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnonymizationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Request testPayload;
    private Request missingDataPayload;
    private Request missingAttributesPayload;
    private Request missingPrivacyModelsPayload;
    private Request testRequestPayloadWithToManyQuasi;

    @BeforeEach
    void setUp() {
        testPayload = GenerateTestData.zipcodeRequestPayload();
        missingDataPayload = GenerateTestData.zipcodeRequestPayloadWithoutData();
        missingAttributesPayload = GenerateTestData.zipcodeRequestPayloadWithoutAttributes();
        missingPrivacyModelsPayload = GenerateTestData.zipcodeRequestPayloadWithoutPrivacyModels();
        testRequestPayloadWithToManyQuasi = GenerateTestData.zipcodeRequestPayload3QuasiNoHierarchies();
    }

    @Test
    void anonymization_post() {
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assert resultData != null;
        assertNotNull(resultData.getRiskProfile().getReIdentificationRisk().getMeasures().get("records_affected_by_highest_prosecutor_risk"));
        assertNotNull(resultData.getAnonymizeResult().getData());
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
    void anonymization_with_payload_containing_to_many_quasi_vs_hierarchies_should_return_bad_request() {
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",testRequestPayloadWithToManyQuasi, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

}