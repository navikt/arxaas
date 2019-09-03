package no.nav.arxaas;

import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.FormDataAttribute;
import no.nav.arxaas.model.FormMetaDataRequest;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;
import no.nav.arxaas.model.risk.AttackerSuccess;
import no.nav.arxaas.model.risk.DistributionOfRisk;
import no.nav.arxaas.model.risk.ReIdentificationRisk;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.model.risk.AttributeRisk;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.DataFactory;
import org.apache.commons.io.IOUtils;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static no.nav.arxaas.model.AttributeTypeModel.*;

public class GenerateTestData {

    public static Request zipcodeRequestPayload() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
    }

    public static Request zipcodeAnalyzationRequestPayload() {
        List<String[]> testData = ageGenderZipcodeData();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(null);
        return new Request(testData, testAttributes, null,null);
    }

    public static Request zipcodeRequestPayloadAfterAnonymization(){
        List<String[]> testData = ageGenderZipcodeDataAfterAnonymization();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(null);
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, null,suppressionLimit);
    }

    public static Data ageGenderZipcodeDataset(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayload());
    }

    public static Data ageGenderZipcodeDataset2Quasi(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayload2Quasi());
    }

    public static Data ageGenderZipcodeDatasetAfterAnonymziation(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayloadAfterAnonymization());
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

        Double suppressionLimit = 0.02;

        return new Request(testData, testAttributes, privacyCriterionModelList,suppressionLimit);
    }

    public static RiskProfile ageGenderZipcodeRiskProfile(){
        var reIdentRisk = ageGenderZipcodeReIndenticationRisk();
        var distributedRisk = ageGenderZipcodeDistributionOfRisk();
        var attributeRisk = ageGenderZipcodeAttributeRisk();
        return new RiskProfile(reIdentRisk, distributedRisk, attributeRisk);
    }

    public static RiskProfile ageGenderZipcodeRiskProfileAfterAnonymization(){
        var reIdentRisk = ageGenderZipcodeReIndenticationRiskAfterAnonymization();
        var distributedRisk = ageGenderZipcodeDistributionOfRiskAfterAnonymization();
        var attributeRisk = ageGenderZipcodeAttributeRisk();
        return new RiskProfile(reIdentRisk, distributedRisk, attributeRisk);
    }

    public static ReIdentificationRisk ageGenderZipcodeReIndenticationRisk(){
        return new ReIdentificationRisk(ageGenderZipcodeMeasures(), attackerSuccessRates(), quasiIdentifiers(), populationModel());
    }

    public static DistributionOfRisk ageGenderZipcodeDistributionOfRisk(){
        return DistributionOfRisk.create(ageGenderZipcodeDataset().getHandle().getRiskEstimator());
    }

    private static DistributionOfRisk ageGenderZipcodeDistributionOfRiskAfterAnonymization(){
        return DistributionOfRisk.create(ageGenderZipcodeDatasetAfterAnonymziation().getHandle().getRiskEstimator());
    }

    private static AttributeRisk ageGenderZipcodeAttributeRisk(){
        ARXPopulationModel pModel= ARXPopulationModel.create(ageGenderZipcodeDataset2Quasi().getHandle().getNumRows(), 0.01d);
        return AttributeRisk.create(ageGenderZipcodeDataset2Quasi().getHandle(), pModel);
    }


    public static Map<String, Double> ageGenderZipcodeMeasures(){
        Map<String,Double > measureMap = new HashMap<>();

        measureMap.put("records_affected_by_highest_prosecutor_risk",1.0);
        measureMap.put("sample_uniques",1.0);
        measureMap.put("estimated_prosecutor_risk",1.0);
        measureMap.put("highest_journalist_risk",1.0);
        measureMap.put("records_affected_by_lowest_risk",1.0);
        measureMap.put("estimated_marketer_risk",1.0);
        measureMap.put("highest_prosecutor_risk",1.0);
        measureMap.put("estimated_journalist_risk",1.0);
        measureMap.put("lowest_risk",1.0);
        measureMap.put("average_prosecutor_risk",1.0);
        measureMap.put("records_affected_by_highest_journalist_risk",1.0);
        measureMap.put("population_uniques",1.0);
        return measureMap;
    }

    public static List<String> quasiIdentifiers(){
        return List.of("zipcode", "gender");
    }

    public static String populationModel(){
        return "ZAYATZ";
    }

    public static AttackerSuccess attackerSuccessRates(){
        Map<String,Double> measureMap = new HashMap<>();
        measureMap.put("Journalist_attacker_success_rate",1.0);
        measureMap.put("Marketer_attacker_success_rate",1.0);
        measureMap.put("Prosecutor_attacker_success_rate",1.0);
        return new AttackerSuccess(measureMap);
    }

    public static MultipartFile ageGenderZipcodeMultipartFile(){
        return makeMockMultipartFile("./src/test/resources/testDataset.csv","file","text/csv");
    }

    public static FormMetaDataRequest formDataTestMetaData(){

        List<FormDataAttribute> formDataAttributeList = new ArrayList<>();
        FormDataAttribute ageAttribute = new FormDataAttribute("age",IDENTIFYING,null);
        FormDataAttribute genderAttribute = new FormDataAttribute("gender",QUASIIDENTIFYING,null);
        FormDataAttribute zipcodeAttribute = new FormDataAttribute("zipcode", QUASIIDENTIFYING,0);
        formDataAttributeList.add(ageAttribute);
        formDataAttributeList.add(genderAttribute);
        formDataAttributeList.add(zipcodeAttribute);

        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));

        Double suppressionLimit = 0.02;

        return new FormMetaDataRequest(formDataAttributeList,privacyCriterionModelList,suppressionLimit);
    }

    private static MultipartFile genderHierarchyMultipartFile(){
        return makeMockMultipartFile("./src/test/resources/testGenderHierarchy.csv", "hierarchies", "text/csv");
    }

    private static MultipartFile zipcodeHierarchyMultipartFile(){
        return makeMockMultipartFile("./src/test/resources/testZipcodeHierarchy.csv","hierarchies", "text/csv");
    }

    public static MultipartFile[] testHierarchiesMultipartFile(){
        return new MultipartFile[]{zipcodeHierarchyMultipartFile(),genderHierarchyMultipartFile()};
    }

    private static MultipartFile makeMockMultipartFile(String pathName, String name, String contentType){
        try {
            File file = new File(pathName);
            FileInputStream input = new FileInputStream(file);
            return new MockMultipartFile(name,file.getName(),contentType, IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ReIdentificationRisk ageGenderZipcodeReIndenticationRiskAfterAnonymization(){
        return new ReIdentificationRisk(
                ageGenderZipcodeMeasuresAfterAnonymization(),
                attackerSuccessRateAfterAnon(),
                quasiIdentifiersAfterAnon(),
                populationModelAfterAnon());
    }

    private static Map<String, Double> ageGenderZipcodeMeasuresAfterAnonymization() {
        Map<String, Double> expected = new HashMap<>();
        expected.put("records_affected_by_highest_prosecutor_risk",0.4545454545454545);
        expected.put("sample_uniques",0.0);
        expected.put("estimated_prosecutor_risk",0.2);
        expected.put("highest_journalist_risk",0.2);
        expected.put("records_affected_by_lowest_risk",0.5454545454545454);
        expected.put("estimated_marketer_risk",0.18181818181818183);
        expected.put("highest_prosecutor_risk",0.2);
        expected.put("estimated_journalist_risk",0.2);
        expected.put("lowest_risk",0.16666666666666664);
        expected.put("average_prosecutor_risk",0.18181818181818183);
        expected.put("records_affected_by_highest_journalist_risk",0.4545454545454545);
        expected.put("population_uniques",0.0);
        return expected;
    }

    private static String populationModelAfterAnon(){
        return "DANKAR";
    }

    private static List<String> quasiIdentifiersAfterAnon(){
        return List.of("zipcode", "gender");
    }

    private static AttackerSuccess attackerSuccessRateAfterAnon(){
        Map<String,Double> measureMap = new HashMap<>();
        measureMap.put("Prosecutor_attacker_success_rate",0.18181818181818183);
        measureMap.put("Journalist_attacker_success_rate",0.18181818181818183);
        measureMap.put("Marketer_attacker_success_rate",0.18181818181818183);
        return new AttackerSuccess(measureMap);
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

        Double suppressionLimit = 0.02;

        return new Request(testData, testAttributes, null, suppressionLimit);
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

        Double suppressionLimit = 0.02;

        return new Request(testData, testAttributes, privacyCriterionModelList,suppressionLimit);
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
