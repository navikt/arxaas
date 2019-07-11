package no.nav.arxaas.model.risk;

import no.nav.arxaas.GenerateTestData;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelSampleSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class AttackerSuccessTest {

    private RiskModelSampleSummary testSummary;

    @BeforeEach
    void setUp() {
        Data testData = GenerateTestData.ageGenderZipcodeDataset();
        double THRESHOLD = 0.5;
        testSummary = testData.getHandle().getRiskEstimator().getSampleBasedRiskSummary(THRESHOLD);
    }


    @Test
    void create_from_RiskModelSampleSummary(){
        AttackerSuccess success = AttackerSuccess.create(testSummary);
        Assertions.assertNotNull(success);
    }

    @Test
    void equals() {
        AttackerSuccess success1 = AttackerSuccess.create(testSummary);
        AttackerSuccess success2 = AttackerSuccess.create(testSummary);
        Assertions.assertEquals(success1, success2);
    }

    @Test
    void testHashCode() {
        AttackerSuccess success1 = AttackerSuccess.create(testSummary);
        AttackerSuccess success2 = AttackerSuccess.create(testSummary);
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Set testSet = Set.of(success1, success2);});
    }
}