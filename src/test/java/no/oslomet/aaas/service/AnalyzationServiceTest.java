package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.risk.RiskProfile;
import no.oslomet.aaas.utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        RiskProfile actual = analyzationService.analyze(testRequestPayload);
        RiskProfile expected = GenerateTestData.ageGenderZipcodeRiskProfile();

        Assert.assertEquals(expected,actual);
    }
}