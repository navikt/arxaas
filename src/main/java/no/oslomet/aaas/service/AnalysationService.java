package no.oslomet.aaas.service;


import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysationResponsePayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXResponseAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AnalysationService {

    private ARXWrapper arxWrapper;
    private ARXPayloadAnalyser arxPayloadAnalyser;
    private ARXResponseAnalyser arxResponseAnalyser;

    @Autowired
    public AnalysationService(ARXWrapper arxWrapper,
                              ARXPayloadAnalyser arxPayloadAnalyser,
                              ARXResponseAnalyser arxResponseAnalyser){
        this.arxResponseAnalyser = arxResponseAnalyser;
        this.arxWrapper = arxWrapper;
        this.arxPayloadAnalyser = arxPayloadAnalyser;
    }


    public AnalysationResponsePayload getPayloadAnalysis(AnalysationPayload payload){
        Data data = arxWrapper.setData(payload.getData());
        arxWrapper.setSensitivityModels(data,payload);
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
        Map<String,String> analasysMetrics = arxPayloadAnalyser.getPayloadAnalysisData(data,pModel);
        return new AnalysationResponsePayload(analasysMetrics);
    }

    public String getResponseAnalysis(AnonymizationPayload payload)throws IOException {
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
        ARXPopulationModel pModel= ARXPopulationModel.create(result.getOutput().getNumRows(), 0.01d);
        return arxResponseAnalyser.getResponseAnalysisData(result,pModel);
    }
}
