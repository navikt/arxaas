package no.oslomet.aaas;

import no.oslomet.aaas.model.*;
import no.oslomet.aaas.model.analytics.DistributionOfRisk;
import no.oslomet.aaas.model.analytics.ReIdentificationRisk;
import no.oslomet.aaas.model.analytics.RiskProfile;
import no.oslomet.aaas.utils.ARXConfigurationFactory;
import no.oslomet.aaas.utils.ARXDataFactory;
import no.oslomet.aaas.utils.ARXPrivacyCriterionFactory;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;

public class GenerateIntegrationTestData {

    public static Request zipcodeRequestPayload() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        return new Request(testData, testAttributes, privacyCriterionModels);
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


    public static RiskProfile zipcodeAnalyzation(){
        return new RiskProfile(ageGenderZipcodeReIndenticationRisk(),ageGenderZipcodeDistributionOfRisk());
    }

    private static Data ageGenderZipcodeDataset(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayload());
    }

    private static DistributionOfRisk ageGenderZipcodeDistributionOfRisk(){
        return DistributionOfRisk.create(ageGenderZipcodeDataset().getHandle().getRiskEstimator());
    }

    private static ReIdentificationRisk ageGenderZipcodeReIndenticationRisk(){
        return new ReIdentificationRisk(ageGenderZipcodeMeasures());
    }

    private static Map<String, String> ageGenderZipcodeMeasures(){
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
        measureMap.put("quasi_identifiers","[zipcode]");
        return measureMap;
    }

    public static RiskProfile zipcodeAnalyzationAfterAnonymization(){
        return new RiskProfile(ageGenderZipcodeReIndenticationRiskAfterAnonymization(),ageGenderZipcodeDistributionOfRiskAfterAnonymization());
    }

    public static Request zipcodeRequestPayloadAfterAnonymization(){
        List<String[]> testData = ageGenderZipcodeDataAfterAnonymization();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(null);
        return new Request(testData, testAttributes, null);
    }

    private static Data ageGenderZipcodeDatasetAfterAnonymziation(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayloadAfterAnonymization());
    }

    private static DistributionOfRisk ageGenderZipcodeDistributionOfRiskAfterAnonymization(){
        return DistributionOfRisk.create(ageGenderZipcodeDatasetAfterAnonymziation().getHandle().getRiskEstimator());
    }

    private static ReIdentificationRisk ageGenderZipcodeReIndenticationRiskAfterAnonymization(){
        return new ReIdentificationRisk(ageGenderZipcodeMeasuresAfterAnonymization());
    }

    private static Map<String, String> ageGenderZipcodeMeasuresAfterAnonymization() {
        Map<String,String> expected = new HashMap<>();
        expected.put("Prosecutor_attacker_success_rate","9.090909090909092");
        expected.put("records_affected_by_highest_prosecutor_risk","100.0");
        expected.put("sample_uniques","0.0");
        expected.put("estimated_prosecutor_risk","9.090909090909092");
        expected.put("population_model","DANKAR");
        expected.put("highest_journalist_risk","9.090909090909092");
        expected.put("records_affected_by_lowest_risk","100.0");
        expected.put("estimated_marketer_risk","9.090909090909092");
        expected.put("Journalist_attacker_success_rate","9.090909090909092");
        expected.put("highest_prosecutor_risk","9.090909090909092");
        expected.put("estimated_journalist_risk","9.090909090909092");
        expected.put("lowest_risk","9.090909090909092");
        expected.put("Marketer_attacker_success_rate","9.090909090909092");
        expected.put("average_prosecutor_risk","9.090909090909092");
        expected.put("records_affected_by_highest_journalist_risk","100.0");
        expected.put("population_uniques","0.0");
        expected.put("quasi_identifiers","[zipcode]");
        return expected;
    }

    private static List<String[]> ageGenderZipcodeDataAfterAnonymization(){
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

    private static AnonymizeResult anonymizeResultTestData(){
        return new AnonymizeResult(ageGenderZipcodeDataAfterAnonymization(),"ANONYMOUS",anonymizationMetrics(),ageGenderZipcodeAttributes(zipcodeHierarchy()));
    }

    private static AnonymizationMetrics anonymizationMetrics(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        ARXConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        Data data = dataFactory.create(zipcodeRequestPayload());
        ARXConfiguration config = configurationFactory.create(zipcodeRequestPayload().getPrivacyModels());
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = null;
        try {
            result = anonymizer.anonymize(data,config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AnonymizationMetrics(result);
    }

    public static AnonymizationResultPayload anonymizationResultPayload(){
        return new AnonymizationResultPayload(anonymizeResultTestData(),zipcodeAnalyzationAfterAnonymization());
    }

}
