package no.oslomet.aaas.model;


import java.util.Map;

/**
 * Model class for result from an anonymization process
 */
public class AnonymizeResult {

    private final String data;
    private final String anonymizationStatus;
    private final MetaData payloadMetaData;
    private final Map<String, String> statistics;

    public AnonymizeResult(String data, String anonymizationStatus,
                           MetaData payloadMetaData,
                           Map<String, String> statistics) {
        this.data = data;
        this.anonymizationStatus = anonymizationStatus;
        this.payloadMetaData = payloadMetaData;
        this.statistics = statistics;
    }

    public String getData() {
        return data;
    }

    public String getAnonymizationStatus() {
        return anonymizationStatus;
    }

    public MetaData getPayloadMetaData() {
        return payloadMetaData;
    }

    public Map<String, String> getStatistics() {
        return statistics;
    }
}
