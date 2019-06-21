package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.risk.RiskProfile;
import no.oslomet.aaas.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnalyzationServiceTest {

    private AnalyzationService analyzationService;
    private DataFactory dataFactory = new ARXDataFactory();
    private ARXAnalyzer arxAnalyser = new ARXAnalyzer(dataFactory);

    @BeforeEach
    public void initialize(){ analyzationService = new AnalyzationService(arxAnalyser); }

    private Request testRequestPayload;
    @BeforeEach
    public void generateTestData() {
        testRequestPayload = GenerateTestData.zipcodeRequestPayload2Quasi();
    }

    @Test
    public void getPayloadAnalysis() {
        RiskProfile actual = analyzationService.analyze(testRequestPayload);
        RiskProfile expected = GenerateTestData.ageGenderZipcodeRiskProfile();

        Assertions.assertEquals(expected,actual);
    }
}