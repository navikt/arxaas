package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.analytics.RiskProfile;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnalyzationService;
import no.oslomet.aaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/analyze")
public class AnalyzationController {

    private final AnalyzationService analyzationService;
    private final LoggerService loggerService;


    @Autowired
    AnalyzationController(AnalyzationService analyzationService, LoggerService loggerService) {
        this.analyzationService = analyzationService;
        this.loggerService = loggerService;
    }

    @PostMapping
    public RiskProfile getPayloadAnalyze(@Valid @RequestBody Request payload, HttpServletRequest request) {
        loggerService.loggPayload(payload, request.getRemoteAddr(), AnalyzationController.class);
        return analyzationService.analyze(payload);
    }

}
