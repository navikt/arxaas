package no.oslomet.aaas.analyser;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;

public class ARXAnalyserTest {

    private Analyser testAnalyser;
    private Request testRequest;

    @Before
    public void setUp() {
        testAnalyser = new ARXAnalyser(new ARXDataFactory(), new ARXPayloadAnalyser());
        testRequest = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void analyse__run() {
        var result = testAnalyser.analyse(testRequest);
    }
}