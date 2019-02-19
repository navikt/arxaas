package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for response object from anonymisation process
 */
public class AnonymizationResponsePayload {

    private final AnonymizeResult anonymizeResult;
    private final Map<String, String> beforeAnonymizationMetrics;
    private final Map<String, String> afterAnonymizationMetrics;



    public AnonymizationResponsePayload(AnonymizeResult anonymizeResult, Map<String, String> beforeAnonymizationMetrics, Map<String, String> afterAnonymisationMetrics) {
        this.anonymizeResult = anonymizeResult;
        this.beforeAnonymizationMetrics = beforeAnonymizationMetrics;
        this.afterAnonymizationMetrics = afterAnonymisationMetrics;

    }

    public AnonymizeResult getAnonymizeResult() {
        return anonymizeResult;
    }

    public Map<String, String> getBeforeAnonymizationMetrics() {
        return beforeAnonymizationMetrics;
    }

    public Map<String, String> getAfterAnonymizationMetrics() {
        return afterAnonymizationMetrics;
    }

}
