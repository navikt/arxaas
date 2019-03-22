package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.anonymizer.ARXAnonymiser;
import no.oslomet.aaas.model.*;
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
        anonymizationService = new AnonymizationService(new ARXAnonymiser(dataFactory,configurationFactory),
                new ARXAnalyzer(new ARXDataFactory(), new ARXPayloadAnalyser()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void anonymize_result() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        List<String[]> actual= test.getAnonymizeResult().getData();
        String[][] rawData = {{"age", "gender", "zipcode" },
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"}};
        List<String[]> expected = List.of(rawData);
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    public void anonymize_beforeAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        Map<String, String> actual = test.getBeforeAnonymizationMetrics();
        Map<String,String > expected = new HashMap<>();
            expected.put("measure_value","[%]");
            expected.put("records_affected_by_highest_risk","100.0");
            expected.put("sample_uniques","100.0");
            expected.put("estimated_prosecutor_risk","100.0");
            expected.put("population_model","ZAYATZ");
            expected.put("records_affected_by_lowest_risk","100.0");
            expected.put("estimated_marketer_risk","100.0");
            expected.put("highest_prosecutor_risk","100.0");
            expected.put("estimated_journalist_risk","100.0");
            expected.put("lowest_risk","100.0");
            expected.put("average_prosecutor_risk","100.0");
            expected.put("population_uniques","100.0");
            expected.put("quasi_identifiers","[zipcode]");

        Assert.assertEquals(expected.keySet(),actual.keySet());
    }

    @Test
    public void anonymize_afterAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testRequestPayload);
        Map<String, String> actual = test.getAfterAnonymizationMetrics();
        Map<String,String> expected = new HashMap<>();
            expected.put("measure_value","[%]");
            expected.put("records_affected_by_highest_risk","45.45454545454545");
            expected.put("sample_uniques","0.0");
            expected.put("estimated_prosecutor_risk","20.0");
            expected.put("population_model","DANKAR");
            expected.put("records_affected_by_lowest_risk","54.54545454545454");
            expected.put("estimated_marketer_risk","18.181818181818183");
            expected.put("highest_prosecutor_risk","20.0");
            expected.put("estimated_journalist_risk","20.0");
            expected.put("lowest_risk","16.666666666666664");
            expected.put("average_prosecutor_risk","18.181818181818183");
            expected.put("population_uniques","0.0");
            expected.put("quasi_identifiers","[zipcode]");
        Assert.assertEquals(expected.keySet(),actual.keySet());
    }
}