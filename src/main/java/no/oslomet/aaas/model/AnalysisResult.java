package no.oslomet.aaas.model;

import java.util.Map;


public class AnalysisResult {

    private final Map<String, String> metrics;

    public AnalysisResult(Map<String, String> metrics) {
        this.metrics = metrics;
    }

    public Map<String, String> getMetrics() {
        return metrics;
    }
}
