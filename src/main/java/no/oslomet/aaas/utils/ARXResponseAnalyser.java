package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ARXResponseAnalyser {

    public double getResponseLowestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    public double getResponseRecordsAffectByRisk(ARXResult result, ARXPopulationModel pModel, double risk){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    public Double getResponseAverageProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getResponseHighestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getResponseEstimatedProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getResponseEstimatedJournalistRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getResponseEstimatedMarketerRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getResponseSampleUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getResponsePopulationUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getResponsePopulationModel(result,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getResponsePopulationModel(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getResponseQuasiIdentifiers(ARXResult result){
        return result.getDataDefinition().getQuasiIdentifyingAttributes();
    }

    public String showResponseAnalysisData(ARXResult result,ARXPopulationModel pModel){
        return  "Measure: Value;[%]\n" +
                "Lowest risk;" + getResponseLowestProsecutorRisk(result,pModel)*100 + "%\n" +
                "Records affected by lowest risk;" + getResponseRecordsAffectByRisk(result,pModel, getResponseLowestProsecutorRisk(result,pModel))*100 + "%\n" +
                "Average prosecutor risk;" + getResponseAverageProsecutorRisk(result,pModel)*100 + "%\n" +
                "Highest prosecutor risk;" + getResponseHighestProsecutorRisk(result,pModel)*100 + "%\n" +
                "Record affected by highest risk;" + getResponseRecordsAffectByRisk(result,pModel, getResponseHighestProsecutorRisk(result,pModel))*100 + "%\n" +
                "Estimated prosecutor risk;" + getResponseEstimatedProsecutorRisk(result,pModel)*100 + "%\n" +
                "Estimated prosecutor risk;" + getResponseEstimatedProsecutorRisk(result,pModel)*100 + "%\n" +
                "Estimated journalist risk;" + getResponseEstimatedJournalistRisk(result,pModel)*100 + "%\n" +
                "Estimated marketer risk;" + getResponseEstimatedMarketerRisk(result,pModel)*100 + "%\n" +
                "Sample uniques: " + getResponseSampleUniques(result,pModel)*100 + "%\n" +
                "Population uniques: " + getResponsePopulationUniques(result,pModel)*100 + "%\n" +
                "Population model: " + getResponsePopulationModel(result,pModel) + "\n" +
                "Quasi-identifiers: " + getResponseQuasiIdentifiers(result)  + "\n";
    }
}