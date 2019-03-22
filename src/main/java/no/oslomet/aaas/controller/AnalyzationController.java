package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalyzeResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnalyzationService;
import no.oslomet.aaas.service.LoggerAnalyzationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyze")
public class AnalyzationController {

    private  final AnalyzationService analyzationService;
    private  final LoggerAnalyzationService loggerAnalyzationService;


    @Autowired
    AnalyzationController(AnalyzationService analyzationService, LoggerAnalyzationService loggerAnalyzationService){
        this.analyzationService = analyzationService;
        this.loggerAnalyzationService = loggerAnalyzationService;
    }


    @PostMapping
    public AnalyzeResult getPayloadAnalyze(@RequestBody Request payload) {
        loggerAnalyzationService.loggAnalyzationPayload(payload);
        return analyzationService.analyze(payload);
    }

}
