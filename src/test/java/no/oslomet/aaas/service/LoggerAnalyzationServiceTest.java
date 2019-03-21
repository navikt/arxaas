package no.oslomet.aaas.service;


import no.oslomet.aaas.controller.AnalyzationController;
import no.oslomet.aaas.controller.AnonymizationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAnalyzationServiceTest {

    private Logger anonymizationLogger = LoggerFactory.getLogger(AnonymizationController.class);
    private Logger analyzationLogger = LoggerFactory.getLogger(AnalyzationController.class);

}

