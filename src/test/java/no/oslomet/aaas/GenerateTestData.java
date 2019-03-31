package no.oslomet.aaas;

import no.oslomet.aaas.model.*;
import no.oslomet.aaas.utils.ARXDataFactory;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;

public class GenerateTestData {


    public static Request zipcodeRequestPayload() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        return new Request(testData, testAttributes, privacyCriterionModels);
    }

    public static Request zipcodeRequestPayloadWithoutData() {
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        return new Request(null, testAttributes, privacyCriterionModels);
    }

    public static Request zipcodeRequestPayloadWithoutAttributes() {
        List<String[]> testData = ageGenderZipcodeData();
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        return new Request(testData, null, privacyCriterionModels);
    }

    public static Request zipcodeRequestPayloadWithoutPrivacyModels() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        return new Request(testData, testAttributes, null);
    }

    public static Data ageGenderZipcodeDataset(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayload());
    }

    public static Request zipcodeRequestPayload2Quasi() {

        List<String[]> testData = ageGenderZipcodeData();

        //Defining Hierarchy for a give column name
        List<String[]> listHierarchy = zipcodeHierarchy();


        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));

        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));

        return new Request(testData, testAttributes, privacyCriterionModelList);
    }


    public static Map<String, String> ageGenderZipcodeMeasures(){
        Map<String,String > measureMap = new HashMap<>();
        measureMap.put("Prosecutor_attacker_success_rate","100.0");
        measureMap.put("records_affected_by_highest_prosecutor_risk","100.0");
        measureMap.put("sample_uniques","100.0");
        measureMap.put("estimated_prosecutor_risk","100.0");
        measureMap.put("population_model","ZAYATZ");
        measureMap.put("highest_journalist_risk","100.0");
        measureMap.put("records_affected_by_lowest_risk","100.0");
        measureMap.put("estimated_marketer_risk","100.0");
        measureMap.put("Journalist_attacker_success_rate","100.0");
        measureMap.put("highest_prosecutor_risk","100.0");
        measureMap.put("estimated_journalist_risk","100.0");
        measureMap.put("lowest_risk","100.0");
        measureMap.put("Marketer_attacker_success_rate","100.0");
        measureMap.put("average_prosecutor_risk","100.0");
        measureMap.put("records_affected_by_highest_journalist_risk","100.0");
        measureMap.put("population_uniques","100.0");
        measureMap.put("quasi_identifiers","[zipcode, gender]");
        return measureMap;
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

        List<String[]> testData = ageGenderZipcodeData();
        List<String []> listAgeHierarchy = ageHierarchy();
        List<String []> listGenderHierarchy = genderHierarchy();
        List<String []> listZipHierarchy = zipcodeHierarchy();

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",QUASIIDENTIFYING, listAgeHierarchy));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, listGenderHierarchy));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listZipHierarchy));

        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","2");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));

        return new Request(testData, testAttributes, privacyCriterionModelList);
    }

    public static Request zipcodeRequestPayload3QuasiNoHierarchies(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String []> listZipHierarchy = zipcodeHierarchy();


        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listZipHierarchy));

        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","2");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));

        return new Request(testData, testAttributes, privacyCriterionModelList);
    }


    private static List<String[]> genderHierarchy() {
        String[][] genderHierarchy={{"male", "*"}, {"female", "*"}};
        return List.of(genderHierarchy);
    }

    private static List<String[]> ageHierarchy() {
        String[][] newAgeHiarchy = {
                {"34", "<50", "*"},
                {"35", "<50", "*"},
                {"36", "<50", "*"},
                {"37", "<50", "*"},
                {"38", "<50", "*"},
                {"39", "<50", "*"},
                {"40", "<50", "*"},
                {"41", "<50", "*"},
                {"42", "<50", "*"},
                {"43", "<50", "*"},
                {"44", "<50", "*"}};
        return List.of(newAgeHiarchy);
    }


    private static List<String[]> ageGenderZipcodeData() {
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
        return  List.of(rawData);
    }

    private static List<String[]> zipcodeHierarchy() {
        //Defining Hierarchy for a give column name
        String[][] testHeirarchy = {
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                , {"81668", "8166*", "816**", "81***", "8****", "*****"}
                , {"81669", "8166*", "816**", "81***", "8****", "*****"}
                , {"81670", "8167*", "816**", "81***", "8****", "*****"}
                , {"81671", "8167*", "816**", "81***", "8****", "*****"}
                , {"81672", "8167*", "816**", "81***", "8****", "*****"}
                , {"81673", "8167*", "816**", "81***", "8****", "*****"}
                , {"81674", "8167*", "816**", "81***", "8****", "*****"}
                , {"81675", "8167*", "816**", "81***", "8****", "*****"}
                , {"81676", "8167*", "816**", "81***", "8****", "*****"}
                , {"81677", "8167*", "816**", "81***", "8****", "*****"}};

        return List.of(testHeirarchy);
    }

    private static List<Attribute> ageGenderZipcodeAttributes(List<String[]> listHierarchy) {
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",SENSITIVE, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));
        return testAttributes;
    }


    private static List<PrivacyCriterionModel> ageGenderZipcodePrivacyModels() {
        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));
        Map<String,String> lMapValue = new HashMap<>();
        lMapValue.put("l", "2");
        lMapValue.put("column_name", "gender");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_DISTINCT, lMapValue));
        return privacyCriterionModelList;
    }
}
