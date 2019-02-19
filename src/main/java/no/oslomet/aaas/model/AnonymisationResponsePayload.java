package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for response object from anonymisation process
 */
public class AnonymisationResponsePayload {

    private final String data;
    private final boolean isAnonymized;
    private final MetaData payloadMetaData;
    private final Map<String, String> beforeAnonymisationMetrics;
    private final Map<String, String> afterAnonymisationMetrics;
    private final Map<String, String> statistics;


    public AnonymisationResponsePayload(String data, boolean isAnonymized, MetaData payloadMetaData, Map<String, String> beforeAnonymisationMetrics, Map<String, String> afterAnonymisationMetrics, Map<String, String> statistics) {
        this.data = data;
        this.isAnonymized = isAnonymized;
        this.payloadMetaData = payloadMetaData;

        this.beforeAnonymisationMetrics = beforeAnonymisationMetrics;
        this.afterAnonymisationMetrics = afterAnonymisationMetrics;
        this.statistics = statistics;
    }

    public String getData() {
        return data;
    }

    public MetaData getPayloadMetaData() {
        return payloadMetaData;
    }

    public Map<String, String> getBeforeAnonymisationMetrics() {
        return beforeAnonymisationMetrics;
    }

    public Map<String, String> getAfterAnonymisationMetrics() {
        return afterAnonymisationMetrics;
    }

    public boolean isAnonymized() {
        return isAnonymized;
    }

    public Map<String, String> getStatistics() {
        return statistics;
    }
}
