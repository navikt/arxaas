package no.oslomet.aaas.service;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.controller.AnalyzationController;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class LoggerAnalyzationServiceTest {

    private AnalyzationService analyzationService;
    private Request testRequestPayload;



    @Before
    public void setUp() {
        DataFactory dataFactory = new ARXDataFactory();
        analyzationService = new AnalyzationService(new ARXAnalyzer(dataFactory));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }


    @Test
    public void loggerAnalysationServiceResult() {
        analyzationService.analyze(testRequestPayload);
        LoggerService loggerAnalysationService = new LoggerService();
        loggerAnalysationService.loggPayload(testRequestPayload, AnalyzationController.class);
        Assertions.assertNotNull(analyzationService.analyze(testRequestPayload));
    }
}

