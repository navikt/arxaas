package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXResponseAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AnonymizationService {
    @Autowired
    ARXWrapper arxWrapper;
    @Autowired
    ARXPayloadAnalyser arxPayloadAnalyser;
    @Autowired
    ARXResponseAnalyser arxResponseAnalyser;

    public String anonymize(AnonymizationPayload payload) throws IOException {
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
       return arxWrapper.getAnonymizeData(result);
    }

    public String getPayloadAnalysis(AnonymizationPayload payload){
        Data data = arxWrapper.setData(payload.getData());
        arxWrapper.setSensitivityModels(data,payload);
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
        return arxPayloadAnalyser.getPayloadAnalysisData(data,pModel);
    }

    public String getResponseAnalysis(AnonymizationPayload payload)throws IOException{
        ARXConfiguration config = ARXConfiguration.create();
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = arxWrapper.anonymize(anonymizer, config, payload);
        ARXPopulationModel pModel= ARXPopulationModel.create(result.getOutput().getNumRows(), 0.01d);
        return arxResponseAnalyser.getResponseAnalysisData(result,pModel);
    }
}

