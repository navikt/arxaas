package no.oslomet.aaas.model;


import no.oslomet.aaas.model.Anonymity;

import java.util.List;
import java.util.Map;

/**
 * Model class for the result from an anonymization process.
 */
public class AnonymizeResult {

    private final List<String[]> data;
    private final String anonymizationStatus;
    private final Map<String, String> statistics;

    /***
     * Setter method for the response object the anonymization process.
     * @param data list of String[] containing the anonymized tabular dataset
     * @param anonymizationStatus String containing the {@link Anonymity} status
     * @param statistics HashMap containing the statistics from the anonymization process. The HashMap contains a
     * String of fields that describe the type of statistic and a String that contains its risk value
     */
    public AnonymizeResult(List<String[]> data, String anonymizationStatus,
                           Map<String, String> statistics) {
        this.data = data;
        this.anonymizationStatus = anonymizationStatus;
        this.statistics = statistics;
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
     * @return HashMap containing the the anonymization statistics. The HashMap contains a
     * String of fields that describe the type of statistic and a String that contains its risk value
     */
    public Map<String, String> getStatistics() {
        return statistics;
    }
}
