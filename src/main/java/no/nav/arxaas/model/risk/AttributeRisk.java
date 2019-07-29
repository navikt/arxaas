package no.nav.arxaas.model.risk;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.risk.RiskModelAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AttributeRisk {

    private final List<QuasiIdentifierRisk> quasiIdentifierRiskList;

    /**
     * Sets a list of distinction and seperation metrics for each of the quasi-identifyig attributes in the dataset
     * @param quasiIdentifierRiskList contains risk factors for each and each combination of risks for quasi-attributes in the dataset
     */
    private AttributeRisk(@JsonProperty("quasiIdentifierRiskList") List<QuasiIdentifierRisk> quasiIdentifierRiskList) {
        this.quasiIdentifierRiskList = quasiIdentifierRiskList;
    }

    /**
     * @return Returns list of distinction and separation risks for each combination of the quasi-identifying attributes.
     */
    public List<QuasiIdentifierRisk> getQuasiIdentifierRiskList(){
        return this.quasiIdentifierRiskList;
    }

    /**
     * Creates a object with type AttributeRisk containing a list of distinction and separation risks for each combination of the quasi-identifying attributes.
     * @param dataToAnalyse The arx DataHandle
     * @param pModel The arx Population model
     * @return A new instance of the class AttributeRisk containing a list of QuasiIdentifierRisks
     */
    public static AttributeRisk create(DataHandle dataToAnalyse, ARXPopulationModel pModel){
        List<QuasiIdentifierRisk> quasiIdentifierRiskList = new ArrayList();
        for (String quasiAttribute : dataToAnalyse.getDefinition().getQuasiIdentifyingAttributes()){
            RiskModelAttributes.QuasiIdentifierRisk[] data = dataToAnalyse.getRiskEstimator(pModel, Set.of(quasiAttribute)).getAttributeRisks().getAttributeRisks();
            for (RiskModelAttributes.QuasiIdentifierRisk quasiRisk : data)
            quasiIdentifierRiskList.add(new QuasiIdentifierRisk(quasiRisk.getIdentifier(), quasiRisk.getDistinction(), quasiRisk.getSeparation()));
        }
        return new AttributeRisk(quasiIdentifierRiskList);
    }

    @Override
    public String toString() {
        return "AttributeRisk{" +
                "quasiIdentifierRiskList=" + quasiIdentifierRiskList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeRisk that = (AttributeRisk) o;
        return Objects.equals(quasiIdentifierRiskList, that.quasiIdentifierRiskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quasiIdentifierRiskList);
    }

    /***
     * Contains distinction and separation risk data for a single combination of quasi-identifying attributes.
     */
    public static class QuasiIdentifierRisk {
        private final List<String> identifier;
        private final double distinction;
        private final double separation;

        /**
         * Constructor for creating a new QuasiIdentifierRisk
         * @param identifier List containing one or more string of attributes
         * @param distinction Percent value of each attribute/attribute combination
         * @param separation Percent value of each attribute/attribute combination
         */
        public QuasiIdentifierRisk(List<String> identifier, double distinction, double separation) {
            this.identifier = identifier;
            this.distinction = distinction;
            this.separation = separation;
        }

        public List<String> getIdentifier() {
            return identifier;
        }

        public double getDistinction() {
            return distinction;
        }

        public double getSeparation() {
            return separation;
        }

        @Override
        public String toString() {
            return "quasiIdentifierRisk{" +
                    "indentifier='" + identifier + '\'' +
                    ", distinction=" + distinction +
                    ", seperation=" + separation +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuasiIdentifierRisk that = (QuasiIdentifierRisk) o;
            return this.hashCode() == that.hashCode();
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier, distinction, separation);
        }


    }
}
