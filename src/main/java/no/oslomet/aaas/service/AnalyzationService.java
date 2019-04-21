package no.oslomet.aaas.service;


import no.oslomet.aaas.analyzer.Analyzer;
import no.oslomet.aaas.model.risk.RiskProfile;

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


    public RiskProfile analyze(Request payload){
        return analyzer.analyze(payload);
    }
}
