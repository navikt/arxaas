package no.nav.arxaas;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;
import no.nav.arxaas.model.Request;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.nav.arxaas.model.AttributeTypeModel.*;

public class GenerateEdgeCaseData {

    public static Request NullPayload(){
        return  new Request(null,null,null,null);
    }

    public static Request zipcodeRequestPayloadWithSuppressionLimitGreaterThan1(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 2.0;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayloadWithSuppressionLimitLessserThan0(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = -1.0;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }


    public static Request zipcodeRequestPayloadWithoutData() {
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(null, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayloadWithoutAttributes() {
        List<String[]> testData = ageGenderZipcodeData();
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, null, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayloadWithoutPrivacyModels() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, null,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_data_format() {
        List<String[]> testData = wrongFromatData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_hierarchy(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = ageHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_privacy_model_on_non_sensitive_data(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes2Quasi(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_privacy_model_format(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcode_wrong_PrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_ldiv_column_key_name(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcode_wrong_LDivColumnKey();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_hierarchy_format(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy_wrong_format();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_wrong_attribute_format(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes_wrong_format(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeRequestPayload_all_format_wrong(){
        List<String[]> testData = wrongFromatData();
        List<String[]> listHierarchy = zipcodeHierarchy_wrong_format();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes_wrong_format(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcode_wrong_PrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
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
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModelList,suppressionLimit);
    }

    public static Request zipcodeRequestPaylaod_hierarchy_having_data_not_included_in_dataset(){
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy_having_data_not_existing_in_dataset();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    private static List<String[]> wrongFromatData() {
        String[][] rawData = {{"age, gender, zipcode" },
                {"34, male, 81667"},
                {"35, female, 81668"},
                {"36, male, 81669"},
                {"37, female, 81670"},
                {"38, male, 81671"},
                {"39, female, 81672"},
                {"40, male, 81673"},
                {"41, female, 81674"},
                {"42, male, 81675"},
                {"43, female, 81676"},
                {"44, male, 81677"}};
        return  List.of(rawData);
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

    public static List<String[]> ageGenderZipcodeDataAfterAnonymization(){
        String[][] rawData = {{"age", "gender", "zipcode" },
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"}};
        return List.of(rawData);
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

    private static List<String[]> zipcodeHierarchy_wrong_format() {
        //Defining Hierarchy for a give column name
        String[][] testHeirarchy = {
                {"81667, 8166*, 816**, 81***, 8****, *****"}
                , {"81668, 8166*, 816**, 81***, 8****, *****"}
                , {"81669, 8166*, 816**, 81***, 8****, *****"}
                , {"81670, 8167*, 816**, 81***, 8****, *****"}
                , {"81671, 8167*, 816**, 81***, 8****, *****"}
                , {"81672, 8167*, 816**, 81***, 8****, *****"}
                , {"81673, 8167*, 816**, 81***, 8****, *****"}
                , {"81674, 8167*, 816**, 81***, 8****, *****"}
                , {"81675, 8167*, 816**, 81***, 8****, *****"}
                , {"81676, 8167*, 816**, 81***, 8****, *****"}
                , {"81677, 8167*, 816**, 81***, 8****, *****"}};

        return List.of(testHeirarchy);
    }

    private static List<String[]> zipcodeHierarchy_having_data_not_existing_in_dataset() {
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
                , {"81677", "8167*", "816**", "81***", "8****", "*****"}
                , {"81678", "8167*", "816**", "81***", "8****", "*****"}
                , {"81679", "8167*", "816**", "81***", "8****", "*****"}
                , {"81680", "8168*", "816**", "81***", "8****", "*****"}
                , {"81681", "8168*", "816**", "81***", "8****", "*****"}
                , {"81682", "8168*", "816**", "81***", "8****", "*****"}};

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

    private static List<Attribute> ageGenderZipcodeAttributes2Quasi(List<String[]> listHierarchy) {
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));
        return testAttributes;
    }

    private static List<Attribute> ageGenderZipcodeAttributes_wrong_format(List<String[]> listHierarchy) {
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("ageE",IDENTIFYING, null));
        testAttributes.add(new Attribute("geNder",QUASIIDENTIFYING, null));
        testAttributes.add(new Attribute("Zipcode",QUASIIDENTIFYING, listHierarchy));
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

    private static List<PrivacyCriterionModel> ageGenderZipcode_wrong_PrivacyModels() {
        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("kl","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));
        Map<String,String> lMapValue = new HashMap<>();
        lMapValue.put("t", "2");
        lMapValue.put("column_names", "gender");
        lMapValue.put("k", "gender");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_DISTINCT, lMapValue));
        return privacyCriterionModelList;
    }

    private static List<PrivacyCriterionModel> ageGenderZipcode_wrong_LDivColumnKey() {
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));

        Map<String,String> lMapValue = new HashMap<>();
        lMapValue.put("l", "2");
        lMapValue.put("label", "gender");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_DISTINCT, lMapValue));
        return privacyCriterionModelList;
    }

    public static MockMultipartFile testDatasetComma(){
       return (MockMultipartFile) GenerateTestData.makeMockMultipartFile("./src/test/resources/testDatasetComma.csv", "file", "text/csv");
    }

    public static MockMultipartFile testDatasetSpecialFormat(){
        return (MockMultipartFile) GenerateTestData.makeMockMultipartFile("./src/test/resources/testDatasetSpecialFormat.csv", "file", "text/csv");
    }

    public static MockMultipartFile testDatasetWrongFormat(){
        return (MockMultipartFile) GenerateTestData.makeMockMultipartFile("./src/test/resources/testDatasetWrongFormat.csv", "file", "text/csv");
    }

    public static MockMultipartFile testMetaDataWrongFormat() {
        return new MockMultipartFile("metadata", "","application/json", testFormData_metadata_two_quasi_attribute_wrong_format().getBytes());
    }

    public static MockMultipartFile testMetaDataThreeQuasiAttribute() {
        return new MockMultipartFile("metadata", "","application/json", testFormData_metadata_three_quasi_attribute().getBytes());
    }

    public static MockMultipartFile testMetaDataMissingPrivacyModel() {
        return new MockMultipartFile("metadata", "","application/json", testFormData_metadata_missing_privacy_models().getBytes());
    }

    public static MockMultipartFile testMetaDataPrivacyModelOnNonExistingAttribute() {
        return new MockMultipartFile("metadata", "","application/json", testFormData_metadata_privacy_models_for_non_existing_attribute().getBytes());
    }

    public static MockMultipartFile testMetaDataWrongPrivacyFormat(){
        return new  MockMultipartFile("metadata", "","application/json", testFormData_metadata_wrong_privacy_model_format().getBytes());
    }

    public static MockMultipartFile testMetaDataSuppressionHigherThanOne(){
        return new  MockMultipartFile("metadata", "","application/json", testFormData_metadata_two_quasi_supression_higher_than_one().getBytes());
    }

    public static MockMultipartFile testMetaDataSuppressionLowerThanZero(){
        return new  MockMultipartFile("metadata", "","application/json", testFormData_metadata_two_quasi_supression_lower_than_zero().getBytes());
    }

    public static MockMultipartFile testMetaDataOneSensitiveAttribute(){
        return new  MockMultipartFile("metadata", "","application/json", testFormData_metadata_one_sensitive_attribute().getBytes());
    }

    public static MockMultipartFile testZipcodeHierarchyWrongFormat() {
        return (MockMultipartFile) GenerateTestData.makeMockMultipartFile("./src/test/resources/testZipcodeHierarchyWrongFormat.csv","hierarchies","text/csv");
    }

    public static MockMultipartFile testZipcodeHierarchyMoreData() {
        return (MockMultipartFile) GenerateTestData.makeMockMultipartFile("./src/test/resources/testZipcodeHierarchyMore.csv","hierarchies","text/csv");
    }

    private static String testFormData_metadata_two_quasi_attribute_wrong_format(){
        return "{\"attributes\":[{\"field\":\"aGe\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"GendEr\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"ZiPcoDe\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}]," +
                "\"suppressionLimit\":0.02}";
    }

    private static String testFormData_metadata_three_quasi_attribute(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}]," +
                "\"suppressionLimit\":0.02}";
    }

    private static String testFormData_metadata_missing_privacy_models(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]}";
    }

    private static String testFormData_metadata_privacy_models_for_non_existing_attribute(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}," +
                                    "{\"privacyModel\":\"LDIVERSITY_DISTINCT\",\"params\":{\"l\":2,\"column_name\":\"Medisinsk forhold\"}}]," +
                "\"suppressionLimit\":0.02}";
    }

    private static String testFormData_metadata_wrong_privacy_model_format(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"LDIVERSITY_DISTINCT\",\"params\":{\"kl\":5}}," +
                "{\"privacyModel\":\"KANONYMITY\",\"params\":{\"l\":2,\"column_name\":\"Medisinsk forhold\"}}]," +
                "\"suppressionLimit\":0.02}";
    }

    private static String testFormData_metadata_two_quasi_supression_higher_than_one(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}]," +
                "\"suppressionLimit\":10000}";
    }

    private static String testFormData_metadata_two_quasi_supression_lower_than_zero(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}]," +
                "\"suppressionLimit\":-10000}";
    }

    private static String testFormData_metadata_one_sensitive_attribute(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"SENSITIVE\",\"hierarchy\":null}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":0}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}," +
                                    "{\"privacyModel\":\"LDIVERSITY_DISTINCT\",\"params\":{\"column_name\":\"gender\",\"l\":2}}]," +
                "\"suppressionLimit\":0.02}";
    }
}