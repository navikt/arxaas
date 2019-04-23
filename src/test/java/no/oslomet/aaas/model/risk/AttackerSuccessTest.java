package no.oslomet.aaas.model.risk;

import no.oslomet.aaas.GenerateTestData;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelSampleSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AttackerSuccessTest {

    private Double THRESHOLD = 0.5;
    private RiskModelSampleSummary testSummary;

    @BeforeEach
    void setUp() {
        Data testData = GenerateTestData.ageGenderZipcodeDataset();
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