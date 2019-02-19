package no.oslomet.aaas.service;

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

    public AnonymizationResponsePayload anonymize(AnonymizationPayload payload){

        AnalysisResult beforeAnalysisResult = analyser.analyse(new AnalysationPayload(payload.getData(),
                payload.getMetaData().getSensitivityList()));

        AnonymizeResult result = this.anonymiser.anonymize(payload);

        AnalysisResult afterAnalysisResult = analyser.analyse(new AnalysationPayload(result.getData(),
                payload.getMetaData().getSensitivityList()));

        return new AnonymizationResponsePayload(result, beforeAnalysisResult.getMetrics(), afterAnalysisResult.getMetrics());
    }

}

