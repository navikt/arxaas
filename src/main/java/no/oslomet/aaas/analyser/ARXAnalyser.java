package no.oslomet.aaas.analyser;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;
import no.oslomet.aaas.utils.ARXModelSetter;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
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

    private final ARXWrapper arxWrapper;
    private final ARXModelSetter arxModelSetter;
    private final ARXPayloadAnalyser arxPayloadAnalyser;

    @Autowired
    public ARXAnalyser(ARXWrapper arxWrapper, ARXModelSetter arxModelSetter, ARXPayloadAnalyser analyser) {
        this.arxWrapper = arxWrapper;
        this.arxModelSetter = arxModelSetter;
        this.arxPayloadAnalyser = analyser;
    }

    @Override
    public AnalysisResult analyse(AnalysationPayload payload) {
        Data data = arxWrapper.createData(payload.getData());
        arxModelSetter.setAttributeTypes(data,payload);
        DataHandle dataToAnalyse = data.getHandle();
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
        Map<String,String> analasysMetrics = arxPayloadAnalyser.getPayloadAnalysisData(dataToAnalyse,pModel);
        return new AnalysisResult(analasysMetrics);
    }
}
