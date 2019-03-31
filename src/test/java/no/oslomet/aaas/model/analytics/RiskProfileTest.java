package no.oslomet.aaas.model.analytics;

import no.oslomet.aaas.GenerateTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RiskProfileTest {

    private RiskProfile testRiskProfile;

    @BeforeEach
    void setUp() {
        testRiskProfile = GenerateTestData.ageGenderZipcodeRiskProfile();
    }

    @Test
    public void getReIdentificationRisk() {
        var reIdRisks = testRiskProfile.getReIdentificationRisk();
        Assertions.assertNotNull(reIdRisks);
    }

    @Test
    public void getDistributionOfRisk() {
        var distRisks = testRiskProfile.getDistributionOfRisk();
        Assertions.assertNotNull(distRisks);
    }

    @Test
    public void equals() {
        var testRiskProfile2 = GenerateTestData.ageGenderZipcodeRiskProfile();
        Assertions.assertEquals(testRiskProfile, testRiskProfile2);
    }

    @Test
    public void testHashCode() {
        var testRiskProfile2 = GenerateTestData.ageGenderZipcodeRiskProfile();
        Assertions.assertThrows(IllegalArgumentException.class, () -> Set.of(testRiskProfile, testRiskProfile2));
    }
}