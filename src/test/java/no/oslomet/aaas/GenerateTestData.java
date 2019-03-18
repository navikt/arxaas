package no.oslomet.aaas;

import no.oslomet.aaas.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;
import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY_DISTINCT;

public class GenerateTestData {

    public static Request zipcodeRequestPayload() {
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

        //Defining Hierarchy for a give column name
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
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}};

        List<String []> listHierarchy = List.of(testHeirarchy);


        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",SENSITIVE, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));

        //Define K-anonymity
        List<PrivacyModelModel> privacyModelModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyModelModelList.add(new PrivacyModelModel(KANONYMITY, kMapValue));
        Map<String,String> lMapValue = new HashMap<>();
        lMapValue.put("l", "2");
        lMapValue.put("column_name", "gender");
        privacyModelModelList.add(new PrivacyModelModel(LDIVERSITY_DISTINCT, lMapValue));

       return new Request(testData, testAttributes, privacyModelModelList);
    }

    public static Request zipcodeRequestPayload2Quasi() {
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

        //Defining Hierarchy for a give column name
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
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}};

        List<String []> listHierarchy = List.of(testHeirarchy);


        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));

        //Define K-anonymity
        List<PrivacyModelModel> privacyModelModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyModelModelList.add(new PrivacyModelModel(KANONYMITY, kMapValue));

        return new Request(testData, testAttributes, privacyModelModelList);
    }

    public static Request zipcodeRequestPayloadHierarchyOverwrite() {
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

        //Defining Hierarchy for a give column name
        String [][] testHeirarchy = {
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81668", "8166*", "816**", "81***", "8****", "*****"}};

        List<String []> listHierarchy = List.of(testHeirarchy);

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, listHierarchy));
        testAttributes.add(new Attribute("gender",SENSITIVE, listHierarchy));
        testAttributes.add(new Attribute("zipcode",INSENSITIVE, listHierarchy));

        return new Request(testData, testAttributes, null);
    }
}
