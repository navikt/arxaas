package no.nav.arxaas.model.risk;

import java.util.Objects;

/**
 * Represents data arxaas risks associated with a dataset
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
                "reIdentificationRisk=" + reIdentificationRisk +
                ", distributionOfRisk=" + distributionOfRisk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskProfile that = (RiskProfile) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(reIdentificationRisk, distributionOfRisk);
    }
}
