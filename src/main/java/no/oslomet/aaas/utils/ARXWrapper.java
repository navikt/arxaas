package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.DistinctLDiversity;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.criteria.PrivacyCriterion;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class ARXWrapper {

    public Data makedata(String rawdata) {
        Data data = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(rawdata.getBytes(StandardCharsets.UTF_8));

        try {
            data = Data.create(stream, Charset.defaultCharset(), ',');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

/*    public Data defineAttri(Data data){
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        data.getDefinition().setAttributeType("age", AttributeType.IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("gender", AttributeType.INSENSITIVE_ATTRIBUTE);
        data.getDefinition().setAttributeType("zipcode", AttributeType.INSENSITIVE_ATTRIBUTE);
        return data;
    }

    public Data defineHeirarchy(Data data ){
        AttributeType.Hierarchy.DefaultHierarchy hierarchy = AttributeType.Hierarchy.create();
        hierarchy.add("81667", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81668", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81669", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81670", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81671", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81672", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81673", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81674", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81675", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81676", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81677", "8167*", "816**", "81***", "8****", "*****");

        data.getDefinition().setAttributeType("zipcode", hierarchy);
        return data;
    }*/

    public ARXConfiguration setsuppressionlimit(ARXConfiguration config){
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


    public ARXConfiguration setPrivacyModels(ARXConfiguration config, AnonymizationPayload payload){
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : payload.getMetaData().getModels().entrySet())
        {
            config.addPrivacyModel(getPrivacyModel(entry.getKey(),entry.getValue()));
        }
        return config;
    }

    public Data setHierarchies(Data data, AnonymizationPayload payload){
        for (Map.Entry<String, String[][]> entry : payload.getMetaData().getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
        return data;
    }

    public PrivacyCriterion getPrivacyModel(PrivacyModel model, Map<String,String> params){
      switch(model){
          case KANONYMITY:
              return new KAnonymity(Integer.parseInt(params.get("k")));
          case LDIVERSITY:
              if(params.get("variant").equals("distinct")){
                  return new DistinctLDiversity(params.get("column_name"),Integer.parseInt(params.get("l")));
              }
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
    public String anonomize (ARXAnonymizer anonymizer, ARXConfiguration config, AnonymizationPayload payload) throws IOException {
        Data data = makedata(payload.getData());
        data = setSensitivityModels(data,payload);
        data = setHierarchies(data, payload);
        config = setsuppressionlimit(config);
        anonymizer = setAnonymizer(anonymizer);
        //File newfile = new File("C:/test.txt");
        ARXResult result = anonymizer.anonymize(data,config);
        DataHandle handle = result.getOutput();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handle.save(outputStream,';');
        return new String(outputStream.toByteArray());
    }

}