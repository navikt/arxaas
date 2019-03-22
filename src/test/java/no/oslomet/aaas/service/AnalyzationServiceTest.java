package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyser.ARXAnalyser;
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
    private ARXPayloadAnalyser arxPayloadAnalyser = new ARXPayloadAnalyser();
    private ARXAnalyser arxAnalyser = new ARXAnalyser(dataFactory, arxPayloadAnalyser);

    @Before
    public void initialize(){ analyzationService = new AnalyzationService(arxAnalyser); }

    private Request testRequestPayload;
    @Before
    public void generateTestData() {
        testRequestPayload = GenerateTestData.zipcodeRequestPayload2Quasi();
    }

    @Test
    public void getPayloadAnalysis() {

        Map<String, String> actual = analyzationService.analyze(testRequestPayload).getMetrics();
        String result100 = "100.0";
        Map<String,String > expected = new HashMap<>();
            expected.put("measure_value","[%]");
            expected.put("records_affected_by_highest_risk",result100);
            expected.put("sample_uniques",result100);
            expected.put("estimated_prosecutor_risk",result100);
            expected.put("population_model","ZAYATZ");
            expected.put("records_affected_by_lowest_risk",result100);
            expected.put("estimated_marketer_risk",result100);
            expected.put("highest_prosecutor_risk",result100);
            expected.put("estimated_journalist_risk",result100);
            expected.put("lowest_risk",result100);
            expected.put("average_prosecutor_risk",result100);
            expected.put("population_uniques",result100);
            expected.put("quasi_identifiers","[zipcode, gender]");
        Assert.assertEquals(expected,actual);
    }
}