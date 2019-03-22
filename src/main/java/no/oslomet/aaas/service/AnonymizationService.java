package no.oslomet.aaas.service;

import no.oslomet.aaas.analyzer.Analyzer;
import no.oslomet.aaas.anonymizer.Anonymiser;
import no.oslomet.aaas.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymizationService {

    private final Anonymiser anonymiser;
    private final Analyzer analyzer;

    @Autowired
    public AnonymizationService(Anonymiser anonymiser, Analyzer analyzer){
        this.anonymiser = anonymiser;
        this.analyzer = analyzer;
    }

    public AnonymizationResultPayload anonymize(Request payload){

       AnalyzeResult beforeAnalyzeResult = analyzer.analyze(payload);

        AnonymizeResult result = this.anonymiser.anonymize(payload);

        Request afterAnalysisPayload =
                new Request(result.getData(), payload.getAttributes(), null);
        AnalyzeResult afterAnalyzeResult = analyzer.analyze(afterAnalysisPayload);

        return new AnonymizationResultPayload(result, beforeAnalyzeResult.getMetrics(), afterAnalyzeResult.getMetrics());
    }

}

