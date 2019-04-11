package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateEdgeCaseData;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.exception.ExceptionResponse;
import no.oslomet.aaas.model.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalyzationEdgeCaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPayloadAnalyze_missing_data(){
        Request missingData = GenerateTestData.zipcodeRequestPayloadWithoutData();
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/analyze",missingData, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void getPayloadAnalyze_wrong_data_format(){
        Request wrongDatasetFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_data_format();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/analyze",wrongDatasetFormat, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }
}
