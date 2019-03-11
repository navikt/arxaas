package no.oslomet.aaas;

import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.utils.ARXConfigurationSetter;
import no.oslomet.aaas.utils.ARXModelSetter;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;
import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;

public class GenerateTestData {

    public static AnonymizationPayload zipcodeAnonymizePayload(){
        AnonymizationPayload testPayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();

        String[][] rawData = {{"age", "gender", "zipcode" },
            {"34", "male", "81667"},
            {"35", "female", "81668"},
            {"36", "male", "81669"},
            {"37", "female", "81670"},
            {"38", "male", "81671"},
            {"39", "female", "81672"},
            {"40", "male", "81673"},
            {"41", "female", "81674"},
            {"42", "male", "81675"},
            {"43", "female", "81676"},
            {"44", "male", "81677"}};
        List<String[]> testData = List.of(rawData);

        testPayload.setData(testData);

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setAttributeTypeList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String ,String[][]> testMapHierarchy = new HashMap<>();
        String [][] testHeirarchy = {
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81668", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81669", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81670", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81671", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81672", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81673", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81674", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81675", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81676", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode",testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel,Map<String,String>> testMapPrivacy = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMapPrivacy.put(KANONYMITY,testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
        return  testPayload;
    }

    public static AnalysationPayload zipcodeAnalysisPayload(){

        String[][] rawData = {{"age", "gender", "zipcode" },
                {"34", "male", "81667"},
                {"35", "female", "81668"},
                {"36", "male", "81669"},
                {"37", "female", "81670"},
                {"38", "male", "81671"},
                {"39", "female", "81672"},
                {"40", "male", "81673"},
                {"41", "female", "81674"},
                {"42", "male", "81675"},
                {"43", "female", "81676"},
                {"44", "male", "81677"}};
        List<String[]> testData = List.of(rawData);
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",SENSITIVE);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);

        return new AnalysationPayload(testData,testMapAttribute);
    }
}
