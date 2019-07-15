package no.nav.arxaas.controller;

import no.nav.arxaas.GenerateIntegrationTestData;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnonymizationSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void anonymzation_system_test(){
        Request testRequestPayload = GenerateIntegrationTestData.zipcodeRequestPayload();
        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testRequestPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        AnonymizationResultPayload actual = Objects.requireNonNull(responseEntity.getBody());
        AnonymizationResultPayload expected = GenerateIntegrationTestData.anonymizationResultPayload();

        Assertions.assertEquals(expected.getRiskProfile(),actual.getRiskProfile());
        for(int x = 0; x<1;x++) {
            assertArrayEquals(expected.getAnonymizeResult().getData().get(x), actual.getAnonymizeResult().getData().get(x));
        }
        for(int x = 0;x<3; x++) {
            Assertions.assertEquals(expected.getAnonymizeResult().getAttributes().get(x).getField(), actual.getAnonymizeResult().getAttributes().get(x).getField());
            Assertions.assertEquals(expected.getAnonymizeResult().getAttributes().get(x).getAttributeTypeModel(), actual.getAnonymizeResult().getAttributes().get(x).getAttributeTypeModel());
        }
        assertEquals(expected.getAnonymizeResult().getAnonymizationStatus(),actual.getAnonymizeResult().getAnonymizationStatus());
        Assertions.assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel());
        Assertions.assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getGeneralizationLevel());
        Assertions.assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getName(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getName());
        Assertions.assertEquals(expected.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getType(),actual.getAnonymizeResult().getMetrics().getAttributeGeneralization().get(0).getType());
        assertEquals(2,actual.getAnonymizeResult().getMetrics().getPrivacyModels().size());
        assertNotNull(actual.getAnonymizeResult().getMetrics().getProcessTimeMillisecounds());
    }
}
