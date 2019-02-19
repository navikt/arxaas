package no.oslomet.aaas.model;

import java.util.Map;


public class AnalysationResponsePayload {

    private final Map<String, String> metrics;

    public AnalysationResponsePayload(Map<String, String> metrics) {
        this.metrics = metrics;
    }

    public Map<String, String> getMetrics() {
        return metrics;
    }
}
