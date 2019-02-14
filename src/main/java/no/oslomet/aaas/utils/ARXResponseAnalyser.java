package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;

import java.util.Set;

public class ARXResponseAnalyser {

    public double getLowestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    public double getRecordsAffectByRisk(ARXResult result, ARXPopulationModel pModel, double risk){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    public Double getAverageProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getHighestProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getEstimatedProsecutorRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getEstimatedJournalistRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getEstimatedMarketerRisk(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getSampleUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getPopulationUniques(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPopulationModel(result,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getPopulationModel(ARXResult result, ARXPopulationModel pModel){
        return result.getOutput()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getQuasiIdentifiers(ARXResult result){
        return result.getDataDefinition().getQuasiIdentifyingAttributes();
    }

    public void showAnalysisData(ARXResult result,ARXPopulationModel pModel){
        System.out.println("Measure: Value [%]");
        System.out.println("Lowest risk: "+getLowestProsecutorRisk(result,pModel)*100);
        System.out.println("Records affected by lowest risk: " + getRecordsAffectByRisk(result,pModel,getLowestProsecutorRisk(result,pModel))*100);
        System.out.println("Average prosecutor risk: " + getAverageProsecutorRisk(result,pModel)*100);
        System.out.println("Highest prosecutor risk: " + getHighestProsecutorRisk(result,pModel)*100);
        System.out.println("Record affected by highest risk: " + getRecordsAffectByRisk(result,pModel,getHighestProsecutorRisk(result,pModel))*100);
        System.out.println("Estimated prosecutor risk: " + getEstimatedProsecutorRisk(result,pModel)*100);
        System.out.println("Estimated journalist risk: " + getEstimatedJournalistRisk(result,pModel)*100);
        System.out.println("Estimated marketer risk: " + getEstimatedMarketerRisk(result,pModel)*100);
        System.out.println("Sample uniques: " + getSampleUniques(result,pModel)*100);
        System.out.println("Population uniques: " + getPopulationUniques(result,pModel)*100);
        System.out.println("Population model: " + getPopulationModel(result,pModel));
        System.out.println("Quasi-identifiers: " + getQuasiIdentifiers(result));
    }
}