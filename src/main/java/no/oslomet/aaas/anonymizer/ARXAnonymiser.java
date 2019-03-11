package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AnonymizeResult;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Anonymizer class using the ARX library to implement the anonymization
 */
@Component
public class ARXAnonymiser implements Anonymiser {

    private final ARXWrapper arxWrapper;

    @Autowired
    public ARXAnonymiser(ARXWrapper arxWrapper) {
        this.arxWrapper = arxWrapper;
    }

    @Override
    public AnonymizeResult anonymize(AnonymizationPayload payload) {
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        try {
            ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
            List<String[]> anonymisedData = arxWrapper.getAnonymizeData(result);

            return new AnonymizeResult(anonymisedData, result.getGlobalOptimum().getAnonymity().toString(), payload.getMetaData(), null);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
