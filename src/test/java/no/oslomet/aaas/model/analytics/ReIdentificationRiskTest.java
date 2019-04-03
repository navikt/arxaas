package no.oslomet.aaas.model.analytics;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.analytics.ReIdentificationRisk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class ReIdentificationRiskTest {

    private Map<String, String> testMeasures;

    @BeforeEach
    void setUp() {
        testMeasures = GenerateTestData.ageGenderZipcodeMeasures();
    }

    @Test
    void getMeasures() {
        ReIdentificationRisk reIdentificationRisk = new ReIdentificationRisk(testMeasures);
        Assertions.assertEquals(testMeasures, reIdentificationRisk.getMeasures());
        testMeasures.put("newkey", "newvalue");
        Assertions.assertNotEquals(testMeasures, reIdentificationRisk.getMeasures());
    }

    @Test
    void equals() {
        ReIdentificationRisk rr1 = new ReIdentificationRisk(testMeasures);
        ReIdentificationRisk rr2= new ReIdentificationRisk(testMeasures);
        Assertions.assertEquals(rr1, rr2);
    }

    @Test
    void testHashCode() {
        ReIdentificationRisk rr1 = new ReIdentificationRisk(testMeasures);
        ReIdentificationRisk rr2= new ReIdentificationRisk(testMeasures);
        Assertions.assertThrows(IllegalArgumentException.class,() -> {Set testSet = Set.of(rr1, rr2);});
    }
}