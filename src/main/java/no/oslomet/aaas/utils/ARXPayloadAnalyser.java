package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;

import java.util.Set;

public class ARXPayloadAnalyser {

    public double getPayloadLowestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    public double getPayloadRecordsAffectByRisk(Data data, ARXPopulationModel pModel, double risk){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    public Double getPayloadAverageProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getPayloadHighestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getPayloadEstimatedProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getPayloadEstimatedJournalistRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getPayloadEstimatedMarketerRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getPayloadSampleUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getPayloadPopulationUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPayloadPopulationModel(data,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getPayloadPopulationModel(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getPayloadQuasiIdentifiers(Data data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
    }

    public String showPayloadAnalysisData(Data data,ARXPopulationModel pModel){
        return  "Measure: Value;[%]\n" +
                "Lowest risk;" + getPayloadLowestProsecutorRisk(data,pModel)*100 + "%\n" +
                "Records affected by lowest risk;" + getPayloadRecordsAffectByRisk(data,pModel, getPayloadLowestProsecutorRisk(data,pModel))*100 + "%\n" +
                "Average prosecutor risk;" + getPayloadAverageProsecutorRisk(data,pModel)*100 + "%\n" +
                "Highest prosecutor risk;" + getPayloadHighestProsecutorRisk(data,pModel)*100 + "%\n" +
                "Record affected by highest risk;" + getPayloadRecordsAffectByRisk(data,pModel, getPayloadHighestProsecutorRisk(data,pModel))*100 + "%\n" +
                "Estimated prosecutor risk;" + getPayloadEstimatedProsecutorRisk(data,pModel)*100 + "%\n" +
                "Estimated prosecutor risk;" + getPayloadEstimatedProsecutorRisk(data,pModel)*100 + "%\n" +
                "Estimated journalist risk;" + getPayloadEstimatedJournalistRisk(data,pModel)*100 + "%\n" +
                "Estimated marketer risk;" + getPayloadEstimatedMarketerRisk(data,pModel)*100 + "%\n" +
                "Sample uniques: " + getPayloadSampleUniques(data,pModel)*100 + "%\n" +
                "Population uniques: " + getPayloadPopulationUniques(data,pModel)*100 + "%\n" +
                "Population model: " + getPayloadPopulationModel(data,pModel) + "\n" +
                "Quasi-identifiers: " + getPayloadQuasiIdentifiers(data)  + "\n";
    }
}
