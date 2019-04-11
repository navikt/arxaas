package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateEdgeCaseData;
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
        Request missingData = GenerateEdgeCaseData.zipcodeRequestPayloadWithoutData();
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

    @Test
    void getPayloadAnalyze_missing_attribute(){
        Request missingAttribute = GenerateEdgeCaseData.zipcodeRequestPayloadWithoutAttributes();
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",missingAttribute, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void getPayloadAnalyze_wrong_attribute_format(){
        Request wrongAttributeFormat = GenerateEdgeCaseData.zipcodeRequestPayload_wrong_attribute_format();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/analyze",wrongAttributeFormat, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void getPayloadAnalyze_null_payload(){
        Request nullPayload = GenerateEdgeCaseData.NullPayload();
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity("/api/anonymize",nullPayload, ExceptionResponse.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }

    @Test
    void getPayloadAnalyze_all_format_wrong(){
        Request allFormatWrong = GenerateEdgeCaseData.zipcodeRequestPayload_all_format_wrong();
        ResponseEntity<IllegalArgumentException> responseEntity = restTemplate.postForEntity("/api/analyze",allFormatWrong, IllegalArgumentException.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assertNotNull(resultData);
        assertNotNull(resultData.getMessage());
    }
}
