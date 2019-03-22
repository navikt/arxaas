package no.oslomet.aaas.service;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.anonymizer.ARXAnonymiser;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class LoggerAnalyzationServiceTest {

    private AnalysationService analysationService;
    private Request testRequestPayload;



    @Before
    public void setUp() {
        DataFactory dataFactory = new ARXDataFactory();
        ConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        analysationService = new AnalysationService(new ARXAnalyser(dataFactory,new ARXPayloadAnalyser()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }


    @Test
    public void loggerAnalysationServiceResult() {
        analysationService.analyse(testRequestPayload);
        LoggerAnalyzationService loggerAnalysationService = new LoggerAnalyzationService();
        loggerAnalysationService.loggAnalyzationPayload(testRequestPayload);
        Assertions.assertNotNull(analysationService.analyse(testRequestPayload));
    }
}

