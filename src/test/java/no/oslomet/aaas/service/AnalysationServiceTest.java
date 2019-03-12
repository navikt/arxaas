package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AnalysationServiceTest {

    private AnalysationService analysationService;
    private DataFactory dataFactory = new ARXDataFactory();
    private ARXPayloadAnalyser arxPayloadAnalyser = new ARXPayloadAnalyser();
    private ARXAnalyser arxAnalyser = new ARXAnalyser(dataFactory, arxPayloadAnalyser);

    @Before
    public void initialize(){ analysationService = new AnalysationService(arxAnalyser); }

    private AnalysationPayload testPayload;
    @Before
    public void generateTestData() {
        testPayload = GenerateTestData.zipcodeAnalysisPayload();
    }

    @Test
    public void getPayloadAnalysis() {
        Map<String, String> actual = analysationService.analyse(testPayload).getMetrics();
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
        Assert.assertEquals(expected,actual);
    }
}