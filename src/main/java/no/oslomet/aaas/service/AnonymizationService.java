package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.model.AnonymisationResponsePayload;
import no.oslomet.aaas.model.AnonymizationPayload;
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

    public AnonymisationResponsePayload anonymize(AnonymizationPayload payload){
        return this.anonymiser.anonymize(payload);
    }

}

