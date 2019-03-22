package no.oslomet.aaas.service;


import no.oslomet.aaas.analyzer.Analyzer;
import no.oslomet.aaas.model.AnalyzeResult;

import no.oslomet.aaas.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnalyzationService {

    private final Analyzer analyzer;

    @Autowired
    public AnalyzationService(Analyzer analyzer){
       this.analyzer = analyzer;
    }


    public AnalyzeResult analyze(Request payload){
        return analyzer.analyze(payload);
    }
}
