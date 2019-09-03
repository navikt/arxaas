package no.nav.arxaas.model.risk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.risk.*;

import java.util.*;

public class ReIdentificationRisk {
    private static final double THRESHOLD = 0.5;

    private final Map<String, Double> measures;
    private final AttackerSuccess attackerSuccessRate;
    private final List<String> quasiIdentifiers;
    private final String populationModel;

    @JsonCreator
    public ReIdentificationRisk(@JsonProperty("measures") Map<String, Double> measures,
                                @JsonProperty("attackerSuccessRate") AttackerSuccess attackerSuccessRate,
                                @JsonProperty("quasiIdentifiers") List<String> quasiIdentifiers,
                                @JsonProperty("populationModel") String populationModel) {
        this.measures  = measures;
        this.attackerSuccessRate = attackerSuccessRate;
        this.quasiIdentifiers = quasiIdentifiers;
        this.populationModel = populationModel;
    }

    public static ReIdentificationRisk create(DataHandle data, ARXPopulationModel pModel){

        RiskEstimateBuilder riskEstimateBuilder = data.getRiskEstimator(pModel);
        Map<String, Double> riskMeasures = riskMeasures(data, pModel);
        AttackerSuccess attackerSuccess = AttackerSuccess
                .create(data.getRiskEstimator(pModel).getSampleBasedRiskSummary(THRESHOLD));
        List<String> quasiIdentifiers = quasiIdentifiers(data);
        String populationModel = populationUniquenessModel(riskEstimateBuilder).toString();
        return new ReIdentificationRisk(riskMeasures, attackerSuccess, quasiIdentifiers,populationModel);
    }

    private static Map<String, Double> riskMeasures(DataHandle data, ARXPopulationModel pModel){
        RiskEstimateBuilder riskEstimateBuilder = data.getRiskEstimator(pModel);
        RiskModelSampleRisks sampleRisks = riskEstimateBuilder.getSampleBasedReidentificationRisk();
        RiskModelSampleRiskDistribution sampleRiskDistribution = riskEstimateBuilder.getSampleBasedRiskDistribution();
        RiskModelSampleSummary riskModelSampleSummary = data.getRiskEstimator(pModel).getSampleBasedRiskSummary(THRESHOLD);

        double lowestRisk = lowestProsecutorRisk(sampleRisks);
        double highestRisk = highestProsecutorRisk(sampleRisks);
        double highestJournalistRisk = highestJournalistRisk(riskModelSampleSummary);

        Map<String, Double> metricsMap = new HashMap<>();
        metricsMap.put("lowest_risk", lowestRisk);
        metricsMap.put("records_affected_by_lowest_risk", recordsAffectByRisk(sampleRiskDistribution, lowestRisk));
        metricsMap.put("average_prosecutor_risk", averageProsecutorRisk(sampleRisks));
        metricsMap.put("highest_prosecutor_risk", highestRisk);
        metricsMap.put("records_affected_by_highest_prosecutor_risk", recordsAffectByRisk(sampleRiskDistribution, highestRisk));
        metricsMap.put("highest_journalist_risk", highestJournalistRisk);
        metricsMap.put("records_affected_by_highest_journalist_risk", recordsAffectByRisk(sampleRiskDistribution, highestJournalistRisk));
        metricsMap.put("estimated_prosecutor_risk", estimatedProsecutorRisk(sampleRisks));
        metricsMap.put("estimated_journalist_risk", estimatedJournalistRisk(sampleRisks));
        metricsMap.put("estimated_marketer_risk", estimatedMarketerRisk(sampleRisks));
        metricsMap.put("sample_uniques", sampleUniques(riskEstimateBuilder.getSampleBasedUniquenessRisk()));
        metricsMap.put("population_uniques",populationUniques(riskEstimateBuilder));
        return metricsMap;
    }

    /***
     * Returns a double that shows the lowest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       lowest risk found in the data set
     */
    private static double lowestProsecutorRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getLowestRisk();
    }

    /***
     * Returns a double that shows the amount of records/fields that are affected by a specific amount of risk.
     * @param sampleRiskDistribution RiskModelSampleRiskDistribution for the dataset
     * @param risk specific amount of risk that affects one or more records
     * @return      records affect by a specific amount of risk
     */
    private static double recordsAffectByRisk(RiskModelSampleRiskDistribution sampleRiskDistribution, double risk){
        return sampleRiskDistribution.getFractionOfRecordsAtRisk(risk);
    }

    /***
     * Returns a double that shows the average prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       average risk found in the data set
     */
    private static double averageProsecutorRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getAverageRisk();
    }

    /***
     * Returns a double that shows the highest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       highest prosecutor risk found in the data set
     */
    private static double highestProsecutorRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getHighestRisk();
    }

    /***
     * Returns a double that shows the estimated prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       estimated prosecutor risk found in the data set
     */
    private static double estimatedProsecutorRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getEstimatedProsecutorRisk();
    }

    /***
     * Returns a double that shows the highest journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleSummary containing summary of the dataset risks
     * @return       highest journalist risk found in the data set
     */
    private static double highestJournalistRisk(RiskModelSampleSummary riskModelSampleSummary){
        return riskModelSampleSummary.getJournalistRisk().getHighestRisk();
    }

    /***
     * Returns a double that shows the estimated journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       estimated journalist risk found in the data set
     */
    private static double estimatedJournalistRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getEstimatedJournalistRisk();
    }

    /***
     * Returns a double that shows the estimated marketer re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleRisks SampleRisks for the dataset
     * @return       estimated marketer risk found in the data set
     */
    private static double estimatedMarketerRisk(RiskModelSampleRisks riskModelSampleRisks){
        return riskModelSampleRisks.getEstimatedMarketerRisk();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set.
     * @param riskModelSampleUniqueness RiskModelSampleUniqueness for the dataset
     * @return      amount of unique records/fields found in the data set
     */
    private static double sampleUniques(RiskModelSampleUniqueness riskModelSampleUniqueness){
        return riskModelSampleUniqueness.getFractionOfUniqueTuples();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set, which are also unique
     * within the underlying population model from which the data is a part of.
     * @param builder RiskEstimateBuilder for the dataset
     * @return      amount of unique records/fields found in the data set which are also unique in the population model
     */
    private static double populationUniques(RiskEstimateBuilder builder){
        return builder.getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(populationUniquenessModel(builder));
    }

    /***
     * Returns the method name used to estimating population uniqueness that assumes that the data set is a uniform
     * sample of the population.
     * @param builder RiskEstimateBuilder for the dataset
     * @return       PopulationUniquenessModel for det dataset
     */
    private static RiskModelPopulationUniqueness.PopulationUniquenessModel populationUniquenessModel(RiskEstimateBuilder builder){
        RiskModelPopulationUniqueness populationBasedUniquenessRisk = builder.getPopulationBasedUniquenessRisk();
        return populationBasedUniquenessRisk.getPopulationUniquenessModel();
    }

    /***
     * Returns a set of strings that contains field names from the data set that has an attribute type of
     * quasi-identifying
     * @param data tabular data set to be analysed against re-identification risk
     * @return      set of strings containing quasi-identifying fields
     */
    private static List<String> quasiIdentifiers(DataHandle data){
        return new ArrayList<>(data.getDefinition().getQuasiIdentifyingAttributes());
    }




    @Override
    public String toString() {
        return "ReIdentificationRisk{" +
                "measures=" + measures +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReIdentificationRisk that = (ReIdentificationRisk) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(measures);
    }

    public AttackerSuccess getAttackerSuccessRate() {
        return attackerSuccessRate;
    }

    public List<String> getQuasiIdentifiers() {
        return quasiIdentifiers;
    }

    public String getPopulationModel() {
        return populationModel;
    }
    public Map<String, Double> getMeasures() {
        return measures;
    }
}
