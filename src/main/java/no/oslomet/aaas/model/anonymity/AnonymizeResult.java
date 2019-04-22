package no.oslomet.aaas.model.anonymity;

import no.oslomet.aaas.model.Attribute;

import java.util.List;

/**
 * Model class for the result from an anonymization process.
 */
public class AnonymizeResult {

    private final List<String[]> data;
    private final String anonymizationStatus;
    private final AnonymizationMetrics metrics;
    private final List<Attribute> attributes;

    /***
     * Setter method for the response object the anonymization process.
     * @param data list of String[] containing the anonymized tabular dataset
     * @param anonymizationStatus String containing the {@link Anonymity} status
     * @param metrics {@link AnonymizationMetrics} containing the metrics from the anonymization process.
     * Contains data on Attribute generalization, privacymodels configuration and time elapsed during anonymization
     */
    public AnonymizeResult(List<String[]> data, String anonymizationStatus,
                           AnonymizationMetrics metrics,List<Attribute> attributes) {
        this.data = data;
        this.anonymizationStatus = anonymizationStatus;
        this.metrics = metrics;
        this.attributes = attributes;
    }

    /***
     * Getter method for the anonymized tabular dataset.
     * @return List of String[] containing the anonymized tabular dataset
     */
    public List<String[]> getData() {
        return data;
    }

    /***
     * Getter method for the {@link Anonymity} status.
     * @return Object of {@link Anonymity} status describing how anonymous that dataset is
     */
    public String getAnonymizationStatus() {
        return anonymizationStatus;
    }

    /***
     * Getter method for the anonymization statistics.
     * @return {@link AnonymizationMetrics} containing the metrics from the anonymization process.
     *      * Contains data on Attribute generalization, privacymodels configuration and time elapsed during anonymization
     */
    public AnonymizationMetrics getMetrics() {
        return metrics;
    }

    public List<Attribute> getAttributes() { return attributes; }
}
