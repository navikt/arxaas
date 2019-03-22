package no.oslomet.aaas.service;


import no.oslomet.aaas.analyser.Analyser;
import no.oslomet.aaas.model.AnalyzeResult;

import no.oslomet.aaas.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnalyzationService {

    private final Analyser analyser;

    @Autowired
    public AnalyzationService(Analyser analyser){
       this.analyser = analyser;
    }


    public AnalyzeResult analyze(Request payload){
        return analyser.analyse(payload);
    }
}
