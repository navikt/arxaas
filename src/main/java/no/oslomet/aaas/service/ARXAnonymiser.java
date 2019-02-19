package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymisationResponsePayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ARXAnonymiser implements Anonymiser {

    private final ARXWrapper arxWrapper;

    @Autowired
    public ARXAnonymiser(ARXWrapper arxWrapper) {
        this.arxWrapper = arxWrapper;
    }

    @Override
    public AnonymisationResponsePayload anonymize(AnonymizationPayload payload) {
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = null;
        try {
            result = arxWrapper.anonymize(anonymizer, config, payload);
            String anonymisedData = arxWrapper.getAnonymizeData(result);
            return new AnonymisationResponsePayload(anonymisedData,
                    true, payload.getMetaData(),
                    null,
                    null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
