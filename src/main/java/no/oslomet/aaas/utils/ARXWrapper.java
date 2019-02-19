package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.*;

import org.deidentifier.arx.ARXResult;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Delivers an interface for reaching the underlying arx functionality of
 *
 */

@Component
public class ARXWrapper {

    public static final char CSV_SEPERATOR_CHAR = ',';
    final String COLUMNNAME = "column_name";
    
    public Data setData(String rawdata) {
        Data data = null;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(rawdata.getBytes(StandardCharsets.UTF_8));
            data = Data.create(stream, Charset.defaultCharset(), ',');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ARXConfiguration setSuppressionLimit(ARXConfiguration config){
        config.setSuppressionLimit(0.02d);
        return config;
    }

    public Data setSensitivityModels(Data data, AnonymizationPayload payload){
        for (Map.Entry<String,SensitivityModel> entry : payload.getMetaData().getSensitivityList().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
        return data;
    }


    public Data setSensitivityModels(Data data, AnalysationPayload analysationPayload){
        for (Map.Entry<String,SensitivityModel> entry : analysationPayload.getAttributeTypes().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
        return data;
    }


    public ARXConfiguration setPrivacyModels(ARXConfiguration config, AnonymizationPayload payload){
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : payload.getMetaData().getModels().entrySet())
        {
            config.addPrivacyModel(getPrivacyModel(entry.getKey(),entry.getValue()));
        }
        return config;
    }

    /**
     *
     * @param data
     * @param payload
     * @return
     */
    public Data setHierarchies(Data data, AnonymizationPayload payload){
        for (Map.Entry<String, String[][]> entry : payload.getMetaData().getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
        return data;
    }

    /**
     * Returns an Arx {@link PrivacyCriterion} object for the desired privacy object selected by the user
     *
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters want set on the created privacy model
     * @return       the {@link PrivacyCriterion} object created with the specified parameters
     */
    public PrivacyCriterion getPrivacyModel(PrivacyModel model, Map<String,String> params){
      switch(model){
          case KANONYMITY:
              return new KAnonymity(Integer.parseInt(params.get("k")));
          case LDIVERSITY_DISTINCT:
              return new DistinctLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")));
          case LDIVERSITY_SHANNONENTROPY:
              return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")), EntropyLDiversity.EntropyEstimator.SHANNON);
          case LDIVERSITY_GRASSBERGERENTROPY:
              return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")), EntropyLDiversity.EntropyEstimator.GRASSBERGER);
          case LDIVERSITY_RECURSIVE:
              return new RecursiveCLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")), Integer.parseInt(params.get("c")));
          default:
              throw new RuntimeException(model.getName() + " Privacy Model not supported");
      }
    }

    public ARXAnonymizer setAnonymizer(ARXAnonymizer anonymizer){
        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        return  anonymizer;
    }

        //remeber we need data perameter
    public ARXResult anonymize(ARXAnonymizer anonymizer, ARXConfiguration config, AnonymizationPayload payload) throws IOException {
        Data data = setData(payload.getData());
        data = setSensitivityModels(data,payload);
        data = setHierarchies(data, payload);
        config = setSuppressionLimit(config);
        config = setPrivacyModels(config,payload);
        anonymizer = setAnonymizer(anonymizer);
        return anonymizer.anonymize(data,config);
    }

    public String getAnonymizeData(ARXResult result) throws IOException {
        DataHandle handle = result.getOutput();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handle.save(outputStream, CSV_SEPERATOR_CHAR);
        return new String(outputStream.toByteArray());
    }
}