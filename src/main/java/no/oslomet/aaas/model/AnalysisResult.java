package no.oslomet.aaas.model;

import java.util.Map;

/**
 * Model class for Analysis result containing a HashMap that holds the analysis results.
 */
public class AnalysisResult {

    private final Map<String, String> metrics;

    /***
     * Setter method for analysis result metrics. Fills the object with the results from the analysis against
     * re-identification risk.
     * @param metrics HashMap containing a String of fields that describe the type of risk and a
     *                String containing its risk value
     */
    public AnalysisResult(Map<String, String> metrics) {
        this.metrics = metrics;
    }

    /***
     * Getter method for the analysis result metrics.
     * @return HashMap containing a String of fields that describe the type of risk and a
     * String containing its risk value
     */
    public Map<String, String> getMetrics() {
        return metrics;
    }
}
