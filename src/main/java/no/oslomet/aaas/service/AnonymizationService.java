package no.oslomet.aaas.service;

import no.oslomet.aaas.analyzer.Analyzer;
import no.oslomet.aaas.anonymizer.Anonymizer;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.AnonymizeResult;
import no.oslomet.aaas.model.analytics.RiskProfile;
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

