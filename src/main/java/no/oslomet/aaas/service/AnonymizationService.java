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

    public AnonymizationResultPayload anonymize(AnonymizationPayload payload){

        AnalysationPayload beforeAnalysisPayload = new AnalysationPayload(payload.getData(),
                payload.getMetaData().getAttributeTypeList());
       AnalysisResult beforeAnalysisResult = analyser.analyse(beforeAnalysisPayload);

        AnonymizeResult result = this.anonymiser.anonymize(payload);

        AnalysationPayload afterAnalysisPayload = new AnalysationPayload(result.getData(),
                payload.getMetaData().getAttributeTypeList());
        AnalysisResult afterAnalysisResult = analyser.analyse(afterAnalysisPayload);

        return new AnonymizationResultPayload(result, beforeAnalysisResult.getMetrics(), afterAnalysisResult.getMetrics());
    }

}

