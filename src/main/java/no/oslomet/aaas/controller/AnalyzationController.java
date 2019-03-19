package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnalysationService;
import no.oslomet.aaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyse")
public class AnalyzationController {

    private  final AnalysationService analysationService;
    private  final LoggerService loggerService;


    @Autowired
    AnalyzationController(AnalysationService analysationService, LoggerService loggerService){
        this.analysationService = analysationService;
        this.loggerService = loggerService;
    }


    @PostMapping
    public AnalysisResult getPayloadAnalysis(@RequestBody Request payload) {
        loggerService.loggAnalyzationPayload(payload);
        return analysationService.analyse(payload);
    }

}
