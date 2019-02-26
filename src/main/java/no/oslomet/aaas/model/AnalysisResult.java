package no.oslomet.aaas.model;

import java.util.Map;

/**
 * Model class for Analysis result
 */
public class AnalysisResult {

    private final Map<String, String> metrics;

    /***
     * Setter method for analysis result metrics. Fills the object with the results from the analysis against
     * re-identification risk.
     * @param metrics HashMap containing fields that describe the type of risk and its value
     */
    public AnalysisResult(Map<String, String> metrics) {
        this.metrics = metrics;
    }

    /***
     * Getter method for the analysis result metrics.
     * @return HashMap containing the results from the analysis against re-identification risk.
     */
    public Map<String, String> getMetrics() {
        return metrics;
    }
}
