package no.nav.arxaas.service;


import no.nav.arxaas.analyzer.Analyzer;
import no.nav.arxaas.model.risk.RiskProfile;

import no.nav.arxaas.model.Request;
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
