package no.oslomet.aaas.model.analytics;

import java.util.Objects;

/**
 * Represents data anonymization risks associated with a dataset
 */
public class RiskProfile {

    private final ReIdentificationRisk reIdentificationRisk;

    private final DistributionOfRisk distributionOfRisk;


    public RiskProfile(ReIdentificationRisk reIdentificationRisk, DistributionOfRisk distributionOfRisk ) {
        this.reIdentificationRisk = reIdentificationRisk;
        this.distributionOfRisk = distributionOfRisk;
    }

    public ReIdentificationRisk getReIdentificationRisk() {
        return reIdentificationRisk;
    }


    public DistributionOfRisk getDistributionOfRisk() {
        return distributionOfRisk;
    }

    @Override
    public String toString() {
        return "RiskProfile{" +
                "reIdentificationRisks=" + reIdentificationRisk +
                ", distributionOfRisks=" + distributionOfRisk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskProfile that = (RiskProfile) o;
        return reIdentificationRisk.equals(that.reIdentificationRisk) &&
                distributionOfRisk.equals(that.distributionOfRisk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reIdentificationRisk, distributionOfRisk);
    }
}
