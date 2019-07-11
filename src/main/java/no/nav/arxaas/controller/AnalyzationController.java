package no.nav.arxaas.controller;

import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.service.AnalyzationService;
import no.nav.arxaas.service.LoggerService;
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
        long requestRecivedTime = System.currentTimeMillis();
        loggerService.loggPayload(payload, request.getRemoteAddr(), AnalyzationController.class);
        RiskProfile analyzationResult = analyzationService.analyze(payload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnalyzationResult(analyzationResult, payload, request.getRemoteAddr(), requestProcessingTime, AnalyzationController.class);
        return analyzationResult;
    }

}
