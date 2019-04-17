package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.analytics.RiskProfile;
import no.oslomet.aaas.service.AnonymizationService;
import no.oslomet.aaas.service.LoggerService;
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
        return anonymizationService.anonymize(payload);
    }

}


