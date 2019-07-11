package no.nav.arxaas.service;

import no.nav.arxaas.analyzer.Analyzer;
import no.nav.arxaas.anonymizer.Anonymizer;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.model.risk.RiskProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymizationService {

    private final Anonymizer anonymizer;
    private final Analyzer analyzer;

    @Autowired
    public AnonymizationService(Anonymizer anonymizer, Analyzer analyzer){
        this.anonymizer = anonymizer;
        this.analyzer = analyzer;
    }

    public AnonymizationResultPayload anonymize(Request payload){

        AnonymizeResult result = this.anonymizer.anonymize(payload);

        Request afterAnalysisPayload =
                new Request(result.getData(), payload.getAttributes(), null, null);
        RiskProfile afterRiskProfile = analyzer.analyze(afterAnalysisPayload);

        return new AnonymizationResultPayload(result, afterRiskProfile);
    }

}

