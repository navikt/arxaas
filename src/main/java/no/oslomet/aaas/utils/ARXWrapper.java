package no.oslomet.aaas.utils;

import org.apache.commons.lang.CharSet;
import org.deidentifier.arx.*;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.ARXResult;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ARXWrapper {



    public Data makedata() {
        DefaultData data = Data.create();
        data.add("age", "gender", "zipcode");
        data.add("34", "male", "81667");
        data.add("35", "female", "81668");
        data.add("36", "male", "81669");
        data.add("37", "female", "81670");
        data.add("38", "male", "81671");
        data.add("39", "female", "81672");
        data.add("40", "male", "81673");
        data.add("41", "female", "81674");
        data.add("42", "male", "81675");
        data.add("43", "female", "81676");
        data.add("44", "male", "81677");
        return data;

    }
    public Data defineAttri(Data data){
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
    }

    public ARXConfiguration setKAnonymity(ARXConfiguration config,int k){

        config.addPrivacyModel(new KAnonymity(k));
        config.setSuppressionLimit(0.02d);
        return config;
    }

    public ARXAnonymizer setAnonymizer(ARXAnonymizer anonymizer){

        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        return  anonymizer;

    }
        //remeber we need data perameter
    public String anonomize (ARXAnonymizer anonymizer,ARXConfiguration config) throws IOException {
        Data data = makedata();
        data = defineAttri(data);
        data = defineHeirarchy(data);
        config = setKAnonymity(config,4);
        anonymizer = setAnonymizer(anonymizer);
        //File newfile = new File("C:/test.txt");
        ARXResult result = anonymizer.anonymize(data,config);
        DataHandle handle = result.getOutput();
        DataHandle view = handle.getView();
        Object value = handle.getValue(0, 2);
        //handle.release();
        //handle.save(newfile,';');
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handle.save(outputStream,';');
        return new String(outputStream.toByteArray());

    }

}