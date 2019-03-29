package no.oslomet.aaas.service;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyzer.ARXAnalyzer;
import no.oslomet.aaas.anonymizer.ARXAnonymizer;
import no.oslomet.aaas.controller.AnonymizationController;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class LoggerServiceTest {

    private AnonymizationService anonymizationService;
    private Request testRequestPayload;

    @Before
    public void setUp() {
        DataFactory dataFactory = new ARXDataFactory();
        ConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        anonymizationService = new AnonymizationService(new ARXAnonymizer(dataFactory,configurationFactory),
                new ARXAnalyzer(new ARXDataFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void loggerAnonymizationServiceResult(){
        anonymizationService.anonymize(testRequestPayload);
        LoggerService loggerService = new LoggerService();
        loggerService.loggPayload(testRequestPayload, "", AnonymizationController.class);
        Assertions.assertNotNull(anonymizationService.anonymize(testRequestPayload));
    }
}

