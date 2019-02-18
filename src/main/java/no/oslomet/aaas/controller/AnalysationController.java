package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.service.AnalysisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyse")
public class AnalysationController {

    private  final AnalysisationService analysisationService;

    @Autowired
    AnalysationController(AnalysisationService analysisationService){
        this.analysisationService = analysisationService;
    }


    @PostMapping
    public AnalysisResult getPayloadAnalysis(@RequestBody AnalysationPayload payload) {
        return analysisationService.analyse(payload);
    }

}
