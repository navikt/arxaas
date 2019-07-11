package no.nav.arxaas.anonymizer;

import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.model.Request;


/**
 *  Public Interface to be forfilled by data anonymizer classes
 */
public interface Anonymizer {

    /**
     * Method to run arxaas on data in the payload with the provided parameters in the payload
     * @param payload {@link Request}  object containing the data to be anonymized and params to use in arxaas
     * @return an {@link AnonymizeResult} object containing the best case arxaas and statistics
     */
    AnonymizeResult anonymize(Request payload);
}
