package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for response object from anonymisation process
 */
public class AnonymisationResponsePayload {

    private final String data;
    private final MetaData payloadMetaData;
    private final Map<String, String> beforeAnonymisationMetrics;
    private final Map<String, String> afterAnonymisationMetrics;

    public AnonymisationResponsePayload(String data, MetaData payloadMetaData, Map<String, String> beforeAnonymisationMetrics, Map<String, String> afterAnonymisationMetrics) {
        this.data = data;
        this.payloadMetaData = payloadMetaData;
        this.beforeAnonymisationMetrics = beforeAnonymisationMetrics;
        this.afterAnonymisationMetrics = afterAnonymisationMetrics;
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
}
