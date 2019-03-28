package no.oslomet.aaas.model;

import java.util.Map;

/**
 * Model class for Analysis result containing a HashMap that holds the analysis results.
 */
public class AnalyzeResult {

    private final Map<String, String> reIdentificationRisk;

    private final DistributionOfRisk distributionOfRisk;

    /***
     * Setter method for analysis result reIdentificationRisk. Fills the object with the results from the analysis against
     * re-identification risk.
     * @param reIdentificationRisk HashMap containing a String of fields that describe the type of risk and a
     *                String containing its risk value
     */
    public AnalyzeResult(Map<String, String> reIdentificationRisk, DistributionOfRisk distributionOfRisk ) {
        this.reIdentificationRisk = reIdentificationRisk;
        this.distributionOfRisk = distributionOfRisk;
    }

    /***
     * Getter method for the analysis result reIdentificationRisk.
     * @return HashMap containing a String of fields that describe the type of risk and a
     * String containing its risk value
     */
    public Map<String, String> getReIdentificationRisk() {
        return reIdentificationRisk;
    }


    public DistributionOfRisk getDistributionOfRisk() {
        return distributionOfRisk;
    }
}
