package no.oslomet.aaas.model;

import java.util.List;
import java.util.Map;

/**
 * Model class for Analysis result containing a HashMap that holds the analysis results.
 */
public class AnalyzeResult {

    private final Map<String, String> metrics;

    private final List<RiskInterval> distributionOfRisk;

    /***
     * Setter method for analysis result metrics. Fills the object with the results from the analysis against
     * re-identification risk.
     * @param metrics HashMap containing a String of fields that describe the type of risk and a
     *                String containing its risk value
     */
    public AnalyzeResult(Map<String, String> metrics, List<RiskInterval> distributionOfRisk ) {
        this.metrics = metrics;
        this.distributionOfRisk = distributionOfRisk;
    }

    /***
     * Getter method for the analysis result metrics.
     * @return HashMap containing a String of fields that describe the type of risk and a
     * String containing its risk value
     */
    public Map<String, String> getMetrics() {
        return metrics;
    }


    public List<RiskInterval> getDistributionOfRisk() {
        return distributionOfRisk;
    }
}
