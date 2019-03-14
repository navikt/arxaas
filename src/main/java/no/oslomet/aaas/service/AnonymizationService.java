package no.oslomet.aaas.service;

import no.oslomet.aaas.analyser.Analyser;
import no.oslomet.aaas.anonymizer.Anonymiser;
import no.oslomet.aaas.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymizationService {

    private final Anonymiser anonymiser;
    private final Analyser analyser;

    @Autowired
    public AnonymizationService(Anonymiser anonymiser, Analyser analyser){
        this.anonymiser = anonymiser;
        this.analyser = analyser;
    }

    public AnonymizationResultPayload anonymize(Request payload){

       AnalysisResult beforeAnalysisResult = analyser.analyse(payload);

        AnonymizeResult result = this.anonymiser.anonymize(payload);

        Request afterAnalysisPayload =
                new Request(result.getData(), payload.getAttributes(), null);
        AnalysisResult afterAnalysisResult = analyser.analyse(afterAnalysisPayload);

        return new AnonymizationResultPayload(result, beforeAnalysisResult.getMetrics(), afterAnalysisResult.getMetrics());
    }

}

