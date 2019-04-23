package no.oslomet.aaas.model.risk;

import no.oslomet.aaas.GenerateTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class ReIdentificationRiskTest {

    private Map<String, Double> testMeasures;
    private AttackerSuccess attackerSuccessRate;
    private List<String> quasiIdentifiers;
    private String populationModel;
    private ReIdentificationRisk reIdentificationRisk;

    @BeforeEach
    void setUp() {
        testMeasures = GenerateTestData.ageGenderZipcodeMeasures();
        attackerSuccessRate = GenerateTestData.attackerSuccessRates();
        quasiIdentifiers = GenerateTestData.quasiIdentifiers();
        populationModel = GenerateTestData.populationModel();
        reIdentificationRisk = new ReIdentificationRisk(testMeasures, attackerSuccessRate, quasiIdentifiers, populationModel);
    }

    @Test
    void getMeasures() {
        Assertions.assertEquals(testMeasures, reIdentificationRisk.getMeasures());
    }

    @Test
    void getAttackerSuccessRate(){
        Assertions.assertEquals(attackerSuccessRate, reIdentificationRisk.getAttackerSuccessRate());
    }

    @Test
    void getPopulationModel(){
        Assertions.assertEquals(populationModel,reIdentificationRisk.getPopulationModel());
    }

    @Test
    void getQuasiIdentifiers(){
        Assertions.assertEquals(quasiIdentifiers,reIdentificationRisk.getQuasiIdentifiers());
    }

    @Test
    void equals() {
        ReIdentificationRisk rr1 = new ReIdentificationRisk(testMeasures, attackerSuccessRate, quasiIdentifiers, populationModel);
        ReIdentificationRisk rr2= new ReIdentificationRisk(testMeasures, attackerSuccessRate, quasiIdentifiers, populationModel);
        Assertions.assertEquals(rr1, rr2);
    }

    @Test
    void testHashCode() {
        ReIdentificationRisk rr1 = new ReIdentificationRisk(testMeasures, attackerSuccessRate, quasiIdentifiers, populationModel);
        ReIdentificationRisk rr2= new ReIdentificationRisk(testMeasures, attackerSuccessRate, quasiIdentifiers, populationModel);
        Assertions.assertThrows(IllegalArgumentException.class,() -> {Set testSet = Set.of(rr1, rr2);});
    }
}