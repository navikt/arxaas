package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;

public class ARXAnalyzerTest {

    private Analyzer testAnalyzer;
    private Request testRequest;

    @Before
    public void setUp() {
        testAnalyzer = new ARXAnalyzer(new ARXDataFactory(), new ARXPayloadAnalyser());
        testRequest = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void analyse__run() {
        var result = testAnalyzer.analyze(testRequest);
    }
}