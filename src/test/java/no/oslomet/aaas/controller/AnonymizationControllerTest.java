package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.model.AnonymizationResultPayload;
import org.junit.jupiter.api.Assertions;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnonymizationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Request testPayload;

    @BeforeEach
    void setUp() {
        testPayload = GenerateTestData.zipcodeRequestPayload2Quasi();
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
    void anonymization_check_for_dataset_values(){
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        List<String[]> actual = Objects.requireNonNull(responseEntity.getBody()).getAnonymizeResult().getData();
        List<String[]> expected = GenerateTestData.ageGenderZipcodeDataAfterAnonymization();
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    void anonymization_check_for_attributes_values(){
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        List<Attribute> actual = Objects.requireNonNull(responseEntity.getBody()).getAnonymizeResult().getAttributes();
        List<Attribute> expected = testPayload.getAttributes();
        for(int x =0;x<3;x++){
            Assertions.assertEquals(expected.get(x).getField(),actual.get(x).getField());
            Assertions.assertEquals(expected.get(x).getAttributeTypeModel(),actual.get(x).getAttributeTypeModel());
        }
    }

    @Test
    void anonymization_check_for_anonymization_status_values(){
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        String actual = Objects.requireNonNull(responseEntity.getBody()).getAnonymizeResult().getAnonymizationStatus();
        Assertions.assertEquals("ANONYMOUS",actual);
    }

    @Test
    void anonymization_check_for_metric_values(){
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        AnonymizationMetrics actual = Objects.requireNonNull(responseEntity.getBody()).getAnonymizeResult().getMetrics();
        assertNotNull(actual.getProcessTimeMillisecounds());
        assertEquals(1, actual.getPrivacyModels().size());

        assertEquals(2, actual.getAttributeGeneralization().size());
        assertEquals(0, actual.getAttributeGeneralization().get(0).getGeneralizationLevel());
        assertEquals("gender", actual.getAttributeGeneralization().get(0).getName());
        assertEquals("QUASI_IDENTIFYING_ATTRIBUTE", actual.getAttributeGeneralization().get(0).getType());
    }
}