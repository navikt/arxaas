package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.service.AnalysationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyse")
public class AnalysationController {

    private  final AnalysationService analysationService;

    @Autowired
    AnalysationController(AnalysationService analysationService){
        this.analysationService = analysationService;
    }


    @PostMapping
    public AnalysisResult getPayloadAnalysis(@RequestBody AnalysationPayload payload) {
        return analysationService.analyse(payload);
    }

}
