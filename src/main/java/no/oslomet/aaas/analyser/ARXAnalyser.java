package no.oslomet.aaas.analyser;

import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Analyser class using the ARX library to implement the analysation
 */
@Component
public class ARXAnalyser implements Analyser {

    private final DataFactory dataFactory;
    private final ARXPayloadAnalyser arxPayloadAnalyser;

    @Autowired
    public ARXAnalyser(DataFactory dataFactory, ARXPayloadAnalyser analyser) {
        this.dataFactory = dataFactory;
        this.arxPayloadAnalyser = analyser;
    }

    @Override
    public AnalysisResult analyse(Request payload) {
        Data data = dataFactory.create(payload);
        DataHandle dataToAnalyse = data.getHandle();
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
        Map<String,String> analysisMetrics = arxPayloadAnalyser.getPayloadAnalysisData(dataToAnalyse,pModel);
        return new AnalysisResult(analysisMetrics);
    }
}
