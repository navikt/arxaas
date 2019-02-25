package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnonymizationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private AnonymizationPayload testPayload;

    @BeforeEach
    void setUp() {
        testPayload = new AnonymizationPayload();

        String testData ="age, gender, zipcode\n" +
                "34, male, 81667\n" +
                "35, female, 81668\n" +
                "36, male, 81669\n" +
                "37, female, 81670\n" +
                "38, male, 81671\n" +
                "39, female, 81672\n" +
                "40, male, 81673\n" +
                "41, female, 81674\n" +
                "42, male, 81675\n" +
                "43, female , 81676\n" +
                "44, male, 81677";

        testPayload.setData(testData);

        MetaData testMetaData = new MetaData();

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, SensitivityModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age", IDENTIFYING);
        testMapAttribute.put("gender", QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setSensitivityList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String, String[][]> testMapHierarchy = new HashMap<>();
        String[][] testHeirarchy = new String[][]{
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                , {"81668", "8166*", "816**", "81***", "8****", "*****"}
                , {"81669", "8166*", "816**", "81***", "8****", "*****"}
                , {"81670", "8167*", "816**", "81***", "8****", "*****"}
                , {"81671", "8167*", "816**", "81***", "8****", "*****"}
                , {"81672", "8167*", "816**", "81***", "8****", "*****"}
                , {"81673", "8167*", "816**", "81***", "8****", "*****"}
                , {"81674", "8167*", "816**", "81***", "8****", "*****"}
                , {"81675", "8167*", "816**", "81***", "8****", "*****"}
                , {"81676", "8167*", "816**", "81***", "8****", "*****"}
                , {"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode", testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel, Map<String, String>> testMapPrivacy = new HashMap<>();
        Map<String, String> testMapValue = new HashMap<>();
        testMapValue.put("k", "5");
        testMapPrivacy.put(KANONYMITY, testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
    }

    @Test
    void anonymization_get() {
        ResponseEntity<AnonymizationPayload> responseEntity = restTemplate.getForEntity("/api/anonymize", AnonymizationPayload.class);
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getData(), "Viktor");
    }

    @Test
    void anonymization_post() {

        ResponseEntity<AnonymizationResultPayload> responseEntity = restTemplate.postForEntity("/api/anonymize",testPayload, AnonymizationResultPayload.class);
        assertNotNull(responseEntity);
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}