package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;

import java.util.Set;

public class ARXPayloadAnalyser {

    public double getLowestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    public double getRecordsAffectByRisk(Data data, ARXPopulationModel pModel, double risk){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    public Double getAverageProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getHighestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getEstimatedProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getEstimatedJournalistRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getEstimatedMarketerRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getSampleUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getPopulationUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPopulationModel(data,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getPopulationModel(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getQuasiIdentifiers(Data data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
    }

    public String showAnalysisData(Data data,ARXPopulationModel pModel){
        return  "Measure: Value;[%]\n" +
                "Lowest risk;" + getLowestProsecutorRisk(data,pModel)*100 + "%\n" +
                "Records affected by lowest risk;" + getRecordsAffectByRisk(data,pModel,getLowestProsecutorRisk(data,pModel))*100 + "%\n" +
                "Average prosecutor risk;" + getAverageProsecutorRisk(data,pModel)*100 + "%\n" +
                "Highest prosecutor risk;" + getHighestProsecutorRisk(data,pModel)*100 + "%\n" +
                "Record affected by highest risk;" + getRecordsAffectByRisk(data,pModel,getHighestProsecutorRisk(data,pModel))*100 + "%\n" +
                "Estimated prosecutor risk;" + getEstimatedProsecutorRisk(data,pModel)*100 + "%\n" +
                "Estimated prosecutor risk;" + getEstimatedProsecutorRisk(data,pModel)*100 + "%\n" +
                "Estimated journalist risk;" + getEstimatedJournalistRisk(data,pModel)*100 + "%\n" +
                "Estimated marketer risk;" + getEstimatedMarketerRisk(data,pModel)*100 + "%\n" +
                "Sample uniques: " + getSampleUniques(data,pModel)*100 + "%\n" +
                "Population uniques: " + getPopulationUniques(data,pModel)*100 + "%\n" +
                "Population model: " + getPopulationModel(data,pModel) + "\n" +
                "Quasi-identifiers: " + getQuasiIdentifiers(data)  + "\n";
    }
}
