package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnalyzeResult;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalysationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Request testPayload;

    @BeforeEach
    void setUp() {
        testPayload = GenerateTestData.zipcodeRequestPayload3Quasi();
    }


    @Test
    void getPayloadAnalysis() {

        ResponseEntity<AnalyzeResult> responseEntity = restTemplate.postForEntity("/api/analyse",testPayload, AnalyzeResult.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assert resultData != null;
        assertNotNull(resultData.getMetrics().get("records_affected_by_highest_risk"));
        assertEquals("]50,100]",resultData.getDistributionOfRisk().get(0).getInterval());
        assertEquals(1.0,resultData.getDistributionOfRisk().get(0).getRecordsWithRiskWithinInteval());
        assertEquals(1.0,resultData.getDistributionOfRisk().get(0).getRecordsWithMaxmalRiskWithinInterval());
        assertNotNull(resultData.getDistributionOfRisk());

    }
}