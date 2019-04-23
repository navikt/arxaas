package no.oslomet.aaas.model.risk;

import no.oslomet.aaas.GenerateTestData;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelSampleSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }
}