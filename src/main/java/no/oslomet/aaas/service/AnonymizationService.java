package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AnonymizationService {
    @Autowired
    ARXWrapper arxWrapper;

    public String anonymize(AnonymizationPayload payload) throws IOException {
        ARXConfiguration config = ARXConfiguration.create();
        arxWrapper.setPrivacyModels(config,payload);
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
       return arxWrapper.showAnonymizeData(result);
    }
}

