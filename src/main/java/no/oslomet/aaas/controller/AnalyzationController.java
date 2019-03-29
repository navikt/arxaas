package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalyzeResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnalyzationService;
import no.oslomet.aaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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
    public AnalyzeResult getPayloadAnalyze(@Valid @RequestBody Request payload, HttpServletRequest request) {
        loggerService.loggPayload(payload, request.getRemoteAddr(), AnalyzationController.class);
        return analyzationService.analyze(payload);
    }

}
