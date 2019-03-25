package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.model.AnalyzeResult;
import no.oslomet.aaas.model.DistributionOfRisk;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.RiskInterval;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Analyzer class using the ARX library to implement the analysation
 */
@Component
public class ARXAnalyzer implements Analyzer {

    private final DataFactory dataFactory;
    private final ARXPayloadAnalyser arxPayloadAnalyser;

    @Autowired
    public ARXAnalyzer(DataFactory dataFactory, ARXPayloadAnalyser analyser) {
        this.dataFactory = dataFactory;
        this.arxPayloadAnalyser = analyser;
    }

    @Override
    public AnalyzeResult analyze(Request payload) {
        Data data = dataFactory.create(payload);
        DataHandle dataToAnalyse = data.getHandle();
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
        Map<String,String> analysisMetrics = arxPayloadAnalyser.getPayloadAnalysisData(dataToAnalyse,pModel);
        List<RiskInterval> listRiskInterval = distributionOfRisk(dataToAnalyse,pModel).getRiskIntervalList();
        return new AnalyzeResult(analysisMetrics,listRiskInterval);
    }

    private DistributionOfRisk distributionOfRisk(DataHandle dataToAnalyse, ARXPopulationModel pModel){
        double[] recordsWithRisk = arxPayloadAnalyser.getDistributionOfRecordsWithRisk(dataToAnalyse,pModel);
        double[] recordsWithMaximalRisk = arxPayloadAnalyser.getDistributionOfRecordsWithMaximalRisk(dataToAnalyse,pModel);
        return DistributionOfRisk.createFromRiskAndMaxRisk(recordsWithRisk,recordsWithMaximalRisk);
    }

}
