package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AnalyzationServiceTest {

    private AnalyzationService analyzationService;
    private DataFactory dataFactory = new ARXDataFactory();
    private ARXAnalyzer arxAnalyser = new ARXAnalyzer(dataFactory);

    @Before
    public void initialize(){ analyzationService = new AnalyzationService(arxAnalyser); }

    private Request testRequestPayload;
    @Before
    public void generateTestData() {
        testRequestPayload = GenerateTestData.zipcodeRequestPayload2Quasi();
    }

    @Test
    public void getPayloadAnalysis() {

        Map<String, String> actual = analyzationService.analyze(testRequestPayload).getReIdentificationRisk();
        String result100 = "100.0";
        Map<String,String > expected = new HashMap<>();
                expected.put("measure_value","[%]");
                expected.put("Prosecutor_attacker_success_rate","100.0");
                expected.put("records_affected_by_highest_prosecutor_risk","100.0");
                expected.put("sample_uniques","100.0");
                expected.put("estimated_prosecutor_risk","100.0");
                expected.put("population_model","ZAYATZ");
                expected.put("highest_journalist_risk","100.0");
                expected.put("records_affected_by_lowest_risk","100.0");
                expected.put("estimated_marketer_risk","100.0");
                expected.put("Journalist_attacker_success_rate","100.0");
                expected.put("highest_prosecutor_risk","100.0");
                expected.put("estimated_journalist_risk","100.0");
                expected.put("lowest_risk","100.0");
                expected.put("Marketer_attacker_success_rate","100.0");
                expected.put("average_prosecutor_risk","100.0");
                expected.put("records_affected_by_highest_journalist_risk","100.0");
                expected.put("population_uniques","100.0");
                expected.put("quasi_identifiers","[zipcode, gender]");
        Assert.assertEquals(expected,actual);
    }
}