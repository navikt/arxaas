package no.nav.arxaas.controller;

import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.service.AnonymizationService;
import no.nav.arxaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
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
    public AnonymizationResultPayload anonymization(@Valid @RequestBody Request payload, HttpServletRequest request) {
        long requestRecivedTime = System.currentTimeMillis();
        loggerService.loggPayload(payload, request.getRemoteAddr(), AnonymizationController.class);
        AnonymizationResultPayload anonymizationResult = anonymizationService.anonymize(payload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnonymizeResult(anonymizationResult,requestProcessingTime, AnonymizationController.class, request.getRemoteAddr());
        return anonymizationResult;
    }

}


