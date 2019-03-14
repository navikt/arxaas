package no.oslomet.aaas.model;


import java.util.List;
import java.util.Map;

/**
 * Model class for the result from an anonymization process.
 */
public class AnonymizeResult {

    private final List<String[]> data;
    private final String anonymizationStatus;
    private final MetaData payloadMetaData;
    private final Map<String, String> statistics;

    /***
     * Setter method for the response object the anonymization process.
     * @param data list of string array containing the anonymized tabular dataset
     * @param anonymizationStatus String containing the {@link Anonymity} status
     * @param payloadMetaData Model class {@link MetaData} containing anonymization and analysation settings
     *                        for the dataset
     * @param statistics HashMap containing the statistics from the anonymization process. The HashMap contains a
     * String of fields that describe the type of statistic and a String that contains its risk value
     */
    public AnonymizeResult(List<String[]> data, String anonymizationStatus,
                           MetaData payloadMetaData,
                           Map<String, String> statistics) {
        this.data = data;
        this.anonymizationStatus = anonymizationStatus;
        this.payloadMetaData = payloadMetaData;
        this.statistics = statistics;
    }

    /***
     * Getter method for the anonymized tabular dataset.
     * @return List of string array containing the anonymized tabular dataset
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
     * Getter method for the model class {@link MetaData} containing the anonymization and analysation settings.
     * @return Object of {@link MetaData} containing the anonymization and analysation settings
     */
    public MetaData getPayloadMetaData() {
        return payloadMetaData;
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
