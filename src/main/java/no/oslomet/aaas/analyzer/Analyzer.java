package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.model.analytics.RiskProfile;
import no.oslomet.aaas.model.Request;

/**
 * Public contract to be forfilled by data anonymization analyze classes
 */
public interface Analyzer {

    /**
     * Method to run analysation on the anonymization metrics of the data in the payload
     * with the provided parameters in the payload.
     * @param payload Request containing the data to be analysed and parameters for the analysation process
     * @return RiskProfile object containing the metrics for the provided data
     */
    RiskProfile analyze(Request payload);
}
