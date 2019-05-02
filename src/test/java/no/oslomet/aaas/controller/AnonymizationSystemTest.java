package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateIntegrationTestData;
import no.oslomet.aaas.model.anonymity.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
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
class AnonymizationSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void anonymzation_integration_test(){
        Request testRequestPayload = GenerateIntegrationTestData.zipcodeRequestPayload();
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testRequestPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        AnonymizationResultPayload actual = Objects.requireNonNull(responseEntity.getBody());
        AnonymizationResultPayload expected = GenerateIntegrationTestData.anonymizationResultPayload();

        assertEquals(expected.getRiskProfile(),actual.getRiskProfile());
        for(int x = 0; x<1;x++) {
            assertArrayEquals(expected.getAnonymizeResult().getData().get(x), actual.getAnonymizeResult().getData().get(x));
        }
        for(int x = 0;x<3; x++) {
            assertEquals(expected.getAnonymizeResult().getAttributes().get(x).getField(), actual.getAnonymizeResult().getAttributes().get(x).getField());
            assertEquals(expected.getAnonymizeResult().getAttributes().get(x).getAttributeTypeModel(), actual.getAnonymizeResult().getAttributes().get(x).getAttributeTypeModel());
        }
        assertEquals(expected.getAnonymizeResult().getAnonymizationStatus(),actual.getAnonymizeResult().getAnonymizationStatus());
        assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel());
        assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel());
        assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getName(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getName());
        assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getType(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getType());
        assertEquals(2,actual.getAnonymizeResult().getMetrics().getPrivacyModels().size());
        assertNotNull(actual.getAnonymizeResult().getMetrics().getProcessTimeMillisecounds());
    }
}
