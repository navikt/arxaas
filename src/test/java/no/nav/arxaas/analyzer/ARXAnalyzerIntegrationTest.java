package no.nav.arxaas.analyzer;

import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.utils.ARXDataFactory;
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