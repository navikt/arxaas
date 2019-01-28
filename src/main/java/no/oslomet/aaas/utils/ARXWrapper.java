package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import org.apache.commons.lang.CharSet;
import org.deidentifier.arx.*;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.ARXResult;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ARXWrapper {



    public Data makedata(String rawdata) {
        Data data = null;
        ByteArrayInputStream stream = new ByteArrayInputStream(rawdata.getBytes(StandardCharsets.UTF_8));
        List<String> datalist = Arrays.asList(rawdata.split("\n"));

        List<String[]> rawDataList = new ArrayList<>();
        datalist.forEach(field -> {
            rawDataList.add(field.split(","));
        });

        data = Data.create(rawDataList);


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
    public String anonomize (ARXAnonymizer anonymizer, ARXConfiguration config, AnonymizationPayload payload) throws IOException {
        Data data = makedata(payload.getData());
        data = defineAttri(data);
        data = defineHeirarchy(data);
        config = setKAnonymity(config,4);
        anonymizer = setAnonymizer(anonymizer);
        //File newfile = new File("C:/test.txt");
        ARXResult result = anonymizer.anonymize(data,config);
        DataHandle handle = result.getOutput();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handle.save(outputStream,';');
        return new String(outputStream.toByteArray());

    }

}