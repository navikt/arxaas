package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.model.analytics.ReIdentificationRisk;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.risk.*;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness.PopulationUniquenessModel;
import org.springframework.stereotype.Component;

import java.util.*;

/***
 * Utility class analysing the tabular data set against re-identification risk
 */
@Component
public class ARXReIdentificationRiskFactory {

    private static final int PRECENT_CONVERT = 100;
    private static final double THRESHOLD = 0.5;

    private ARXReIdentificationRiskFactory(){}


    public static ReIdentificationRisk create(DataHandle data, ARXPopulationModel pModel){
        Map<String, String> measures = reIdentificationRisk(data, pModel);
        return new ReIdentificationRisk(measures);
    }



    /***
     * Returns a map containing the different statistics found from the data set.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return a hash map containing data set re-identification statistics
     */
    private static Map<String, String> reIdentificationRisk(DataHandle data, ARXPopulationModel pModel){
        RiskEstimateBuilder riskEstimateBuilder = data.getRiskEstimator(pModel);
        RiskModelSampleRisks sampleRisks = riskEstimateBuilder.getSampleBasedReidentificationRisk();
        RiskModelSampleRiskDistribution sampleRiskDistribution = riskEstimateBuilder.getSampleBasedRiskDistribution();
        RiskModelSampleSummary riskModelSampleSummary = data.getRiskEstimator(pModel).getSampleBasedRiskSummary(THRESHOLD);
        RiskModelPopulationUniqueness populationBasedUniquenessRisk = riskEstimateBuilder.getPopulationBasedUniquenessRisk();


        double lowestRisk = lowestProsecutorRisk(sampleRisks);
        double highestRisk = highestProsecutorRisk(sampleRisks);
        double highestJournalistRisk = highestJournalistRisk(riskModelSampleSummary);

        Map<String, String> metricsMap = new HashMap<>();
        metricsMap.put("lowest_risk", String.valueOf(lowestRisk  * PRECENT_CONVERT));
        metricsMap.put("records_affected_by_lowest_risk", String.valueOf(recordsAffectByRisk(sampleRiskDistribution, lowestRisk) * PRECENT_CONVERT));
        metricsMap.put("average_prosecutor_risk", String.valueOf(averageProsecutorRisk(sampleRisks) * PRECENT_CONVERT));
        metricsMap.put("highest_prosecutor_risk", String.valueOf(highestRisk * PRECENT_CONVERT));
        metricsMap.put("records_affected_by_highest_prosecutor_risk", String.valueOf(recordsAffectByRisk(sampleRiskDistribution, highestRisk) * PRECENT_CONVERT));
        metricsMap.put("Prosecutor_attacker_success_rate",String.valueOf(prosecutorAttackSuccessRate(riskModelSampleSummary) * PRECENT_CONVERT));
        metricsMap.put("highest_journalist_risk", String.valueOf(highestJournalistRisk(riskModelSampleSummary) * PRECENT_CONVERT));
        metricsMap.put("records_affected_by_highest_journalist_risk", String.valueOf(recordsAffectByRisk(sampleRiskDistribution, highestJournalistRisk) * PRECENT_CONVERT));
        metricsMap.put("Journalist_attacker_success_rate",String.valueOf(journalistAttackerSuccessRate(riskModelSampleSummary) * PRECENT_CONVERT));
        metricsMap.put("Marketer_attacker_success_rate",String.valueOf(marketerAttackerSuccessRate(riskModelSampleSummary) * PRECENT_CONVERT));
        metricsMap.put("estimated_prosecutor_risk", String.valueOf(estimatedProsecutorRisk(sampleRisks) * PRECENT_CONVERT));
        metricsMap.put("estimated_journalist_risk", String.valueOf(estimatedJournalistRisk(sampleRisks) * PRECENT_CONVERT));
        metricsMap.put("estimated_marketer_risk", String.valueOf(estimatedMarketerRisk(sampleRisks) * PRECENT_CONVERT));
        metricsMap.put("sample_uniques", String.valueOf(sampleUniques(riskEstimateBuilder.getSampleBasedUniquenessRisk())* PRECENT_CONVERT));
        metricsMap.put("population_uniques", String.valueOf(populationUniques(populationBasedUniquenessRisk)* PRECENT_CONVERT));
        metricsMap.put("population_model", populationUniquenessModel(populationBasedUniquenessRisk).toString());
        metricsMap.put("quasi_identifiers", quasiIdentifiers(data).toString());
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
     * Returns a double that shows the highest journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param riskModelSampleSummary containing summary of the dataset risks
     * @return       highest journalist risk found in the data set
     */
    private static double highestJournalistRisk(RiskModelSampleSummary riskModelSampleSummary){
        return riskModelSampleSummary.getJournalistRisk().getHighestRisk();
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
     * @param riskModelPopulationUniqueness RiskModelPopulationUniqueness for the dataset
     * @return      amount of unique records/fields found in the data set which are also unique in the population model
     */
    private static double populationUniques(RiskModelPopulationUniqueness riskModelPopulationUniqueness){
        return riskModelPopulationUniqueness
                .getFractionOfUniqueTuples(populationUniquenessModel(riskModelPopulationUniqueness));
    }

    /***
     * Returns the method name used to estimating population uniqueness that assumes that the data set is a uniform
     * sample of the population.
     * @param riskModelPopulationUniqueness RiskModelPopulationUniqueness for the dataset
     * @return       PopulationUniquenessModel for det dataset
     */
    private static PopulationUniquenessModel populationUniquenessModel(RiskModelPopulationUniqueness riskModelPopulationUniqueness){
        return riskModelPopulationUniqueness.getPopulationUniquenessModel();
    }

    /***
     * Returns a set of strings that contains field names from the data set that has an attribute type of
     * quasi-identifying
     * @param data tabular data set to be analysed against re-identification risk
     * @return      set of strings containing quasi-identifying fields
     */
    private static Set<String> quasiIdentifiers(DataHandle data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
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

}
