package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalyzeResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnalysationService;
import no.oslomet.aaas.service.LoggerAnalyzationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyse")
public class AnalyzationController {

    private  final AnalysationService analysationService;
    private  final LoggerAnalyzationService loggerAnalyzationService;


    @Autowired
    AnalyzationController(AnalysationService analysationService, LoggerAnalyzationService loggerAnalyzationService){
        this.analysationService = analysationService;
        this.loggerAnalyzationService = loggerAnalyzationService;
    }


    @PostMapping
    public AnalyzeResult getPayloadAnalysis(@RequestBody Request payload) {
        loggerAnalyzationService.loggAnalyzationPayload(payload);
        return analysationService.analyse(payload);
    }

}
