package no.nav.arxaas.service;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.analyzer.ARXAnalyzer;
import no.nav.arxaas.controller.AnalyzationController;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.DataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class LoggerAnalyzationServiceTest {

    private AnalyzationService analyzationService;
    private Request testRequestPayload;



    @BeforeEach
    public void setUp() {
        DataFactory dataFactory = new ARXDataFactory();
        analyzationService = new AnalyzationService(new ARXAnalyzer(dataFactory));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }


    @Test
    public void loggerAnalysationServiceResult() {
        analyzationService.analyze(testRequestPayload);
        LoggerService loggerAnalysationService = new LoggerService();
        loggerAnalysationService.loggPayload(testRequestPayload, " ",AnalyzationController.class);
        Assertions.assertNotNull(analyzationService.analyze(testRequestPayload));
    }
}

