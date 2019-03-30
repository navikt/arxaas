package no.oslomet.aaas.model;

import java.util.Map;

/**
 * Model class for Analysis result containing a HashMap that holds the analysis results.
 */
public class RiskProfile {

    private final Map<String, String> reIdentificationRisk;

    private final DistributionOfRisk distributionOfRisk;


    public RiskProfile(Map<String, String> reIdentificationRisk, DistributionOfRisk distributionOfRisk ) {
        this.reIdentificationRisk = reIdentificationRisk;
        this.distributionOfRisk = distributionOfRisk;
    }

    public Map<String, String> getReIdentificationRisk() {
        return reIdentificationRisk;
    }


    public DistributionOfRisk getDistributionOfRisk() {
        return distributionOfRisk;
    }
}
