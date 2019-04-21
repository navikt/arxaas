package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.anonymizer.ARXAnonymizer;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.model.anonymity.AnonymizationResultPayload;
import no.oslomet.aaas.utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [TODO] MOCK Anonymizer and Analyzer dependencies
 */

public class AnonymizationServiceTest {

    private AnonymizationService anonymizationService;
    private Request testRequestPayload;

    @Before
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
        Map<String, String> actual = test.getRiskProfile().getReIdentificationRisk().getMeasures();
        Map<String,String> expected = new HashMap<>();
                expected.put("Prosecutor_attacker_success_rate","18.181818181818183");
                expected.put("records_affected_by_highest_prosecutor_risk","45.45454545454545");
                expected.put("sample_uniques","0.0");
                expected.put("estimated_prosecutor_risk","20.0");
                expected.put("population_model","DANKAR");
                expected.put("highest_journalist_risk","20.0");
                expected.put("records_affected_by_lowest_risk","54.54545454545454");
                expected.put("estimated_marketer_risk","18.181818181818183");
                expected.put("Journalist_attacker_success_rate","18.181818181818183");
                expected.put("highest_prosecutor_risk","20.0");
                expected.put("estimated_journalist_risk","20.0");
                expected.put("lowest_risk","16.666666666666664");
                expected.put("Marketer_attacker_success_rate","18.181818181818183");
                expected.put("average_prosecutor_risk","18.181818181818183");
                expected.put("records_affected_by_highest_journalist_risk","45.45454545454545");
                expected.put("population_uniques","0.0");
                expected.put("quasi_identifiers","[zipcode, gender]");
        Assert.assertEquals(expected.keySet(),actual.keySet());
    }


}