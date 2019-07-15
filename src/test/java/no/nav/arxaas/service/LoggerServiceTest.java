package no.nav.arxaas.service;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.analyzer.ARXAnalyzer;
import no.nav.arxaas.anonymizer.ARXAnonymizer;
import no.nav.arxaas.controller.AnonymizationController;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class LoggerServiceTest {

    private AnonymizationService anonymizationService;
    private Request testRequestPayload;

    @BeforeEach
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

