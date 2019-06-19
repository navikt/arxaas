package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.risk.RiskProfile;
import no.oslomet.aaas.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ARXAnalyzerIntegrationTest {

    private Analyzer testAnalyzer;
    private Request testRequest;

    @BeforeEach
    public void setUp() {
        testAnalyzer = new ARXAnalyzer(new ARXDataFactory());
        testRequest = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void analyse__run() {
        RiskProfile result = testAnalyzer.analyze(testRequest);
    }

    @Test
    public void analyze_should_return_with_a_re_identification_risk_object(){
        RiskProfile result = testAnalyzer.analyze(testRequest);
        Assertions.assertNotNull(result.getReIdentificationRisk());
    }

    @Test
    public void analyze_should_return_with_a_distribution_of_risk_object(){
        RiskProfile result = testAnalyzer.analyze(testRequest);
        Assertions.assertNotNull(result.getDistributionOfRisk());
    }
}