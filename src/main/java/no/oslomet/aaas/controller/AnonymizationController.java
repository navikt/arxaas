package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnonymizationService;
import no.oslomet.aaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/anonymize")
public class AnonymizationController {

    private final AnonymizationService anonymizationService;
    private final LoggerService loggerService;

    @Autowired
    AnonymizationController(AnonymizationService anonymizationService, LoggerService loggerService) {
        this.anonymizationService = anonymizationService;
        this.loggerService = loggerService;
    }

    @PostMapping
    public AnonymizationResultPayload anonymization(@Valid @RequestBody Request payload) {
        loggerService.loggPayload(payload, AnonymizationController.class);
        return anonymizationService.anonymize(payload);
    }

}
