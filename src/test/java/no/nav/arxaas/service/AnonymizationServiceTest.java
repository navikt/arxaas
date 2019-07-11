package no.nav.arxaas.service;

import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.analyzer.ARXAnalyzer;
import no.nav.arxaas.anonymizer.ARXAnonymizer;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.*;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [TODO] MOCK Anonymizer and Analyzer dependencies
 */

public class AnonymizationServiceTest {

    private AnonymizationService anonymizationService;
    private Request testRequestPayload;

    @BeforeEach
    public void setUp() {
        DataFactory dataFactory = new ARXDataFactory();
        ConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        anonymizationService = new AnonymizationService(new ARXAnonymizer(dataFactory,configurationFactory),
                new ARXAnalyzer(new ARXDataFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void anonymize_result() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        List<String[]> actual= test.getAnonymizeResult().getData();

        List<String[]> expected = GenerateTestData.ageGenderZipcodeDataAfterAnonymization();
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    public void anonymize_afterAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        Map<String, Double> actual = test.getRiskProfile().getReIdentificationRisk().getMeasures();
        Map<String,Double> expected = new HashMap<>();
                expected.put("records_affected_by_highest_prosecutor_risk",0.4545454545454545);
                expected.put("sample_uniques",0.0);
                expected.put("estimated_prosecutor_risk",0.2);
                expected.put("highest_journalist_risk",0.2);
                expected.put("records_affected_by_lowest_risk",0.5454545454545454);
                expected.put("estimated_marketer_risk",0.18181818181818183);
                expected.put("highest_prosecutor_risk",0.2);
                expected.put("estimated_journalist_risk",0.2);
                expected.put("lowest_risk",0.16666666666666664);
                expected.put("average_prosecutor_risk",0.18181818181818183);
                expected.put("records_affected_by_highest_journalist_risk",0.4545454545454545);
                expected.put("population_uniques",0.0);
        Assertions.assertEquals(expected.keySet(),actual.keySet());
    }

    @Test
    public void anonymize_afterAnonymization_AttackerSuccessRates() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        Map<String, Double> actual = test.getRiskProfile().getReIdentificationRisk().getAttackerSuccessRate().getSuccessRates();
        Map<String,Double> expected = new HashMap<>();
        expected.put("Prosecutor_attacker_success_rate",0.18181818181818183);
        expected.put("Journalist_attacker_success_rate",0.18181818181818183);
        expected.put("Marketer_attacker_success_rate",0.18181818181818183);
        Assertions.assertEquals(expected.keySet(),actual.keySet());
    }

    @Test
    public void anonymize_afterAnonymization_QuasiIdentifiers() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        List<String> actual = test.getRiskProfile().getReIdentificationRisk().getQuasiIdentifiers();
        List<String> expected = List.of("zipcode");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void anonymize_afterAnonymization_PopulationModel() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        String actual = test.getRiskProfile().getReIdentificationRisk().getPopulationModel();
        String expected = "DANKAR";
        Assertions.assertEquals(expected,actual);
    }

}