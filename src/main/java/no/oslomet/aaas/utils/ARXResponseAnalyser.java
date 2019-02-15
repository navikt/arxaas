package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;

import java.util.Set;

public class ARXResponseAnalyser {

    public double getPayloadLowestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    public double getPayloadRecordsAffectByRisk(ARXResult result, ARXPopulationModel pModel, double risk){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    public Double getPayloadAverageProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getPayloadHighestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getPayloadEstimatedProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getPayloadEstimatedJournalistRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getPayloadEstimatedMarketerRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getPayloadSampleUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getPayloadPopulationUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPayloadPopulationModel(result,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getPayloadPopulationModel(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getPayloadQuasiIdentifiers(ARXResult result){
        return result.getDataDefinition().getQuasiIdentifyingAttributes();
    }

    public String showPayloadAnalysisData(ARXResult result,ARXPopulationModel pModel){
        return  "Measure: Value;[%]\n" +
                "Lowest risk;" + getPayloadLowestProsecutorRisk(result,pModel)*100 + "%\n" +
                "Records affected by lowest risk;" + getPayloadRecordsAffectByRisk(result,pModel, getPayloadLowestProsecutorRisk(result,pModel))*100 + "%\n" +
                "Average prosecutor risk;" + getPayloadAverageProsecutorRisk(result,pModel)*100 + "%\n" +
                "Highest prosecutor risk;" + getPayloadHighestProsecutorRisk(result,pModel)*100 + "%\n" +
                "Record affected by highest risk;" + getPayloadRecordsAffectByRisk(result,pModel, getPayloadHighestProsecutorRisk(result,pModel))*100 + "%\n" +
                "Estimated prosecutor risk;" + getPayloadEstimatedProsecutorRisk(result,pModel)*100 + "%\n" +
                "Estimated prosecutor risk;" + getPayloadEstimatedProsecutorRisk(result,pModel)*100 + "%\n" +
                "Estimated journalist risk;" + getPayloadEstimatedJournalistRisk(result,pModel)*100 + "%\n" +
                "Estimated marketer risk;" + getPayloadEstimatedMarketerRisk(result,pModel)*100 + "%\n" +
                "Sample uniques: " + getPayloadSampleUniques(result,pModel)*100 + "%\n" +
                "Population uniques: " + getPayloadPopulationUniques(result,pModel)*100 + "%\n" +
                "Population model: " + getPayloadPopulationModel(result,pModel) + "\n" +
                "Quasi-identifiers: " + getPayloadQuasiIdentifiers(result)  + "\n";
    }
}