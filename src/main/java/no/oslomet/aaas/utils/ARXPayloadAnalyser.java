package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness.PopulationUniquenessModel;
import org.springframework.stereotype.Component;

import java.util.*;

/***
 * Utility class analysing the tabular data set against re-identification risk
 */
@Component
public class ARXPayloadAnalyser {

    private static final int PRECENT_CONVERT = 100;
    private static final double THRESHOLD = 0.5;

    private ARXPayloadAnalyser(){}

    /***
     * Returns a double that shows the lowest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set
     * @return       lowest risk found in the data set
     */
    static double getPayloadLowestProsecutorRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    /***
     * Returns a double that shows the amount of records/fields that are affected by a specific amount of risk.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @param risk specific amount of risk that affects one or more records
     * @return       records affect by a specific amount of risk
     */
    static double getPayloadRecordsAffectByRisk(DataHandle data, ARXPopulationModel pModel, double risk){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    /***
     * Returns a double that shows the average prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       average risk found in the data set
     */
    static double getPayloadAverageProsecutorRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    /***
     * Returns a double that shows the highest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       highest prosecutor risk found in the data set
     */
    static double getPayloadHighestProsecutorRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    /***
     * Returns a double that shows the highest journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       highest journalist risk found in the data set
     */
    static double getPayloadHighestJournalistRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskSummary(THRESHOLD)
                .getJournalistRisk()
                .getHighestRisk();
    }

    /***
     * Returns a double that shows the estimated prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated prosecutor risk found in the data set
     */
    static double getPayloadEstimatedProsecutorRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    /***
     * Returns a double that shows the estimated journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated journalist risk found in the data set
     */
    static double getPayloadEstimatedJournalistRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    /***
     * Returns a double that shows the estimated marketer re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated marketer risk found in the data set
     */
    static double getPayloadEstimatedMarketerRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return      amount of unique records/fields found in the data set
     */
    static double getPayloadSampleUniques(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set, which are also unique
     * within the underlying population model from which the data is a part of.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return      amount of unique records/fields found in the data set which are also unique in the population model
     */
    static double getPayloadPopulationUniques(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPayloadPopulationModel(data,pModel));
    }

    /***
     * Returns the method name used to estimating population uniqueness that assumes that the data set is a uniform
     * sample of the population.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       population model name
     */
    static PopulationUniquenessModel getPayloadPopulationModel(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    /***
     * Returns a set of strings that contains field names from the data set that has an attribute type of
     * quasi-identifying
     * @param data tabular data set to be analysed against re-identification risk
     * @return      set of strings containing quasi-identifying fields
     */
    static Set<String> getPayloadQuasiIdentifiers(DataHandle data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
    }

    /***
     * Returns a double that shows the Success rate of a prosecutor risk
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return attacker success rate of a prosecutor risk
     */
    static double getPayloadProsecutorAttackSuccessRate(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskSummary(THRESHOLD)
                .getProsecutorRisk()
                .getSuccessRate();
    }

    /***
     * Returns a double taht shows the success rate of a journalist risk
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return attacker success rate of a journalist risk
     */
    static double getPayloadJournalistAttackerSuccessRate(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskSummary(THRESHOLD)
                .getJournalistRisk()
                .getSuccessRate();
    }

    /***
     * Returns a double taht shows the success rate of a marketer risk
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return attacker success rate of a marketer risk
     */
    static double getPayloadMarketerAttackerSuccessRate(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskSummary(THRESHOLD)
                .getMarketerRisk()
                .getSuccessRate();

    }

    /***
     * Returns a double[] that contains Risk records on the different prosecutor risk ranges.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return double[] that contains Risk records on the different prosecutor risk ranges
     */
    public static double[] getPayloadDistributionOfRecordsWithRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsForRiskThresholds();
    }

    /***
     * Returns a double[] that contains maximal risk records on the different prosecutor risk ranges.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return double[] that contains maximal risk records on the different prosecutor risk ranges
     */
    public static double[] getPayloadDistributionOfRecordsWithMaximalRisk(DataHandle data, ARXPopulationModel pModel){
        return data.getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsForCumulativeRiskThresholds();
    }

    /***
     * Returns a map containing the different statistics found from the data set.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return a hash map containing data set re-identification statistics
     */
    public static Map<String, String> getPayloadAnalyzeData(DataHandle data, ARXPopulationModel pModel){
        Map<String, String> metricsMap = new HashMap<>();
        metricsMap.put("measure_value", "[%]");
        metricsMap.put("lowest_risk", String.valueOf(getPayloadLowestProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("records_affected_by_lowest_risk", String.valueOf(getPayloadRecordsAffectByRisk(data,pModel, getPayloadLowestProsecutorRisk(data,pModel))* PRECENT_CONVERT));
        metricsMap.put("average_prosecutor_risk", String.valueOf(getPayloadAverageProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("highest_prosecutor_risk", String.valueOf(getPayloadHighestProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("records_affected_by_highest_prosecutor_risk", String.valueOf(getPayloadRecordsAffectByRisk(data,pModel, getPayloadHighestProsecutorRisk(data,pModel))* PRECENT_CONVERT));
        metricsMap.put("Prosecutor_attacker_success_rate",String.valueOf(getPayloadProsecutorAttackSuccessRate(data,pModel)*PRECENT_CONVERT));
        metricsMap.put("highest_journalist_risk", String.valueOf(getPayloadHighestJournalistRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("records_affected_by_highest_journalist_risk", String.valueOf(getPayloadRecordsAffectByRisk(data,pModel, getPayloadHighestJournalistRisk(data,pModel))* PRECENT_CONVERT));
        metricsMap.put("Journalist_attacker_success_rate",String.valueOf(getPayloadJournalistAttackerSuccessRate(data,pModel)*PRECENT_CONVERT));
        metricsMap.put("Marketer_attacker_success_rate",String.valueOf(getPayloadMarketerAttackerSuccessRate(data,pModel)*PRECENT_CONVERT));
        metricsMap.put("estimated_prosecutor_risk", String.valueOf(getPayloadEstimatedProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("estimated_journalist_risk", String.valueOf(getPayloadEstimatedJournalistRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("estimated_marketer_risk", String.valueOf(getPayloadEstimatedMarketerRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("sample_uniques", String.valueOf(getPayloadSampleUniques(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("population_uniques", String.valueOf(getPayloadPopulationUniques(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("population_model", getPayloadPopulationModel(data,pModel).toString());
        metricsMap.put("quasi_identifiers", getPayloadQuasiIdentifiers(data).toString());
        return metricsMap;
    }
}
