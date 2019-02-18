package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXResponseAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AnonymizationService {

    private ARXWrapper arxWrapper;

    @Autowired
    public AnonymizationService(ARXWrapper arxWrapper){
        this.arxWrapper = arxWrapper;
    }

    public String anonymize(AnonymizationPayload payload) throws IOException {
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
       return arxWrapper.getAnonymizeData(result);
    }

}

