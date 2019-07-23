package no.nav.arxaas.service;

import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.analyzer.ARXAnalyzer;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.DataFactory;
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