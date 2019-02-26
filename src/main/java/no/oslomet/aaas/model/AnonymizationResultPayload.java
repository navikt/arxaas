package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for response object from anonymisation process
 */
public class AnonymizationResultPayload {

    private final AnonymizeResult anonymizeResult;
    private final Map<String, String> beforeAnonymizationMetrics;
    private final Map<String, String> afterAnonymizationMetrics;

    /***
     * Setter method for the response object from anonymisation and analysation process
     * @param anonymizeResult model {@link AnonymizeResult} containing the anonymized dataset and the metadata
     *                        used for the anonymization
     * @param beforeAnonymizationMetrics HashMap containing the analysis data before the anonymization process
     * @param afterAnonymisationMetrics HashMap containing the analysis data after the anonymization process
     */
    public AnonymizationResultPayload(AnonymizeResult anonymizeResult,
                                      Map<String, String> beforeAnonymizationMetrics,
                                      Map<String, String> afterAnonymisationMetrics) {
        this.anonymizeResult = anonymizeResult;
        this.beforeAnonymizationMetrics = beforeAnonymizationMetrics;
        this.afterAnonymizationMetrics = afterAnonymisationMetrics;

    }

    /***
     * Getter method for the {@link AnonymizeResult} model class containing the anonymized dataset and the metadata used
     * for the anonymization
     * @return {@link AnonymizeResult} containing the dataset and metadata after the anonymization process
     */
    public AnonymizeResult getAnonymizeResult() {
        return anonymizeResult;
    }

    /***
     * Getter method for the analysis data before the anonymization process
     * @return HashMap containing the analysis data before the anonymization process
     */
    public Map<String, String> getBeforeAnonymizationMetrics() {
        return beforeAnonymizationMetrics;
    }

    /***
     * Getter method for the analysis data after teh anonymization process
     * @return HashMap containing the analysis data after the anonymization process
     */
    public Map<String, String> getAfterAnonymizationMetrics() {
        return afterAnonymizationMetrics;
    }

}
