package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.model.risk.RiskProfile;
import no.oslomet.aaas.model.Request;

/**
 * Public contract to be forfilled by data anonymization analyze classes
 */
public interface Analyzer {

    /**
     * Method to run analyzation on the anonymization metrics of the data in the payload
     * with the provided parameters in the payload.
     * @param payload Request containing the data to be analyzed and parameters for the analyzation process
     * @return RiskProfile object containing the metrics for the provided data
     */
    RiskProfile analyze(Request payload);
}
