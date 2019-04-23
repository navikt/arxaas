package no.oslomet.aaas.model.risk;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.risk.RiskModelSampleSummary;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttackerSuccess {

    private final Map<String, Double> successRates;

    @JsonCreator
    public AttackerSuccess(Map<String, Double> successRates) {
        this.successRates = successRates;
    }

    public static AttackerSuccess create(RiskModelSampleSummary riskModelSampleSummary) {
        Map<String, Double> successRates = new HashMap<>();
        successRates.put("Journalist_attacker_success_rate",journalistAttackerSuccessRate(riskModelSampleSummary));
        successRates.put("Marketer_attacker_success_rate",marketerAttackerSuccessRate(riskModelSampleSummary));
        successRates.put("Prosecutor_attacker_success_rate",prosecutorAttackSuccessRate(riskModelSampleSummary));
        return new AttackerSuccess(successRates);
    }


    /***
     * Returns a double that shows the Success rate of a prosecutor risk
     * @param riskModelSampleSummary containing summary of the dataset risks
     * @return attacker success rate of a prosecutor risk
     */
    private static double prosecutorAttackSuccessRate(RiskModelSampleSummary riskModelSampleSummary){
        return riskModelSampleSummary.getProsecutorRisk().getSuccessRate();
    }

    /***
     * Returns a double taht shows the success rate of a journalist risk
     * @param riskModelSampleSummary containing summary of the dataset risks
     * @return attacker success rate of a journalist risk
     */
    private static double journalistAttackerSuccessRate(RiskModelSampleSummary riskModelSampleSummary){
        return riskModelSampleSummary.getJournalistRisk().getSuccessRate();
    }

    /***
     * Returns a double taht shows the success rate of a marketer risk
     * @param riskModelSampleSummary containing summary of the dataset risks
     * @return attacker success rate of a marketer risk
     */
    private static double marketerAttackerSuccessRate(RiskModelSampleSummary riskModelSampleSummary){
        return riskModelSampleSummary.getMarketerRisk().getSuccessRate();
    }

    public Map<String, Double> getSuccessRates() {
        return successRates;
    }

    @Override
    public String toString() {
        return "AttackerSuccess{" +
                "successRates=" + successRates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttackerSuccess success = (AttackerSuccess) o;
        return successRates.equals(success.successRates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(successRates);
    }
}
