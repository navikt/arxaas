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

    public static Request zipcodeRequestPayload3Quasi(){
        // Define data
        String[][] rawData = {{"age", "gender", "zipcode"},
                {"45", "female", "81675"},
                {"34", "male", "81667"},
                {"66", "male", "81925"},
                {"70", "female", "81931"},
                {"34", "female", "81931"},
                {"70", "male", "81931"},
                {"45", "male", "81931"}};
        List<String[]> testData = List.of(rawData);

        // Define hierarchies
        String[][] ageHierarchy ={{"34", "<50", "*"},
                {"45", "<50", "*"},
                {"66", ">=50", "*"},
                {"70", ">=50", "*"}};
        List<String []> listAgeHierarchy = List.of(ageHierarchy);

        String[][] genderHierarchy={{"male", "*"},
                {"female", "*"}};

        List<String []> listGenderHierarchy = List.of(genderHierarchy);

        String[][] zipcodeHierarcy={{"81667", "8166*", "816**", "81***", "8****", "*****"},
                {"81675", "8167*", "816**", "81***", "8****", "*****"},
                {"81925", "8192*", "819**", "81***", "8****", "*****"},
                {"81931", "8193*", "819**", "81***", "8****", "*****"}};
        List<String []> listZipHierarchy = List.of(zipcodeHierarcy);

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",QUASIIDENTIFYING, listAgeHierarchy));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, listGenderHierarchy));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listZipHierarchy));

        //Define K-anonymity
        List<PrivacyModelModel> privacyModelModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","2");
        privacyModelModelList.add(new PrivacyModelModel(KANONYMITY, kMapValue));

        return new Request(testData, testAttributes, privacyModelModelList);
    }
}
