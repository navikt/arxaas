package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateTestData;
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

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnonymizationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private AnonymizationPayload testPayload;

    @BeforeEach
    void setUp() {
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
    void anonymization_get() {
        ResponseEntity<AnonymizationPayload> responseEntity = restTemplate.getForEntity("/api/anonymize", AnonymizationPayload.class);
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    @Test
    void anonymization_post() {
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        var resultData = responseEntity.getBody();
        assert resultData != null;
        assertNotNull(resultData.getAfterAnonymizationMetrics().get("records_affected_by_highest_risk"));
        assertNotNull(resultData.getAnonymizeResult().getData());
    }
}