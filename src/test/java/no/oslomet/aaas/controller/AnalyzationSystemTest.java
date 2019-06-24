package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateIntegrationTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.risk.RiskProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalyzationSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPayloadAnalyze_system_test(){
        Request testRequestPayload = GenerateIntegrationTestData.zipcodeRequestPayload();
        ResponseEntity<RiskProfile> responseEntity = restTemplate.postForEntity("/api/analyze",testRequestPayload, RiskProfile.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        RiskProfile actual = Objects.requireNonNull(responseEntity.getBody());
        RiskProfile expected = GenerateIntegrationTestData.zipcodeAnalyzation();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getPayloadAnalyze_system_test_after_anonymization(){
        Request testRequestPayload = GenerateIntegrationTestData.zipcodeRequestPayloadAfterAnonymization();
        ResponseEntity<RiskProfile> responseEntity = restTemplate.postForEntity("/api/analyze",testRequestPayload, RiskProfile.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        RiskProfile actual = Objects.requireNonNull(responseEntity.getBody());
        RiskProfile expected = GenerateIntegrationTestData.zipcodeAnalyzationAfterAnonymization();
        Assertions.assertEquals(expected,actual);
    }
}
