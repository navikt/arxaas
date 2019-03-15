package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.model.AnonymizeResult;
import no.oslomet.aaas.model.Request;


/**
 *  Public Interface to be forfilled by data anonymizer classes
 */
public interface Anonymiser {

    /**
     * Method to run anonymization on data in the payload with the provided parameters in the payload
     * @param payload Model object containing the data to be anonymized and params to use in anonymization
     * @return AnonymizeResult result object containing the best case anonymization and statistics
     */
    AnonymizeResult anonymize(Request payload);
}
