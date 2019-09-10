package no.nav.arxaas;

import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.anonymity.AnonymizationMetrics;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;
import no.nav.arxaas.model.risk.*;
import no.nav.arxaas.utils.ARXConfigurationFactory;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.ARXPrivacyCriterionFactory;
import no.nav.arxaas.utils.DataFactory;
import org.deidentifier.arx.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.nav.arxaas.model.AttributeTypeModel.*;

public class GenerateIntegrationTestData {

    public static Request zipcodeRequestPayload() {
        List<String[]> testData = ageGenderZipcodeData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, privacyCriterionModels,suppressionLimit);
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
        return new RiskProfile(ageGenderZipcodeReIndenticationRisk(),ageGenderZipcodeDistributionOfRisk(), attributeRisk());
    }

    private static Data ageGenderZipcodeDataset(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayload());
    }

    private static DistributionOfRisk ageGenderZipcodeDistributionOfRisk(){
        return DistributionOfRisk.create(ageGenderZipcodeDataset().getHandle().getRiskEstimator());
    }

    private static ReIdentificationRisk ageGenderZipcodeReIndenticationRisk(){
        return new ReIdentificationRisk(ageGenderZipcodeMeasures(), attackerSuccessRate(), quasiIdentifiers(), populationModel());
    }

    private static Map<String, Double> ageGenderZipcodeMeasures(){
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

    private static String populationModel(){
        return "ZAYATZ";
    }

    private static List<String> quasiIdentifiers(){
        return List.of("quasi_identifiers","zipcode");
    }

    private static AttackerSuccess attackerSuccessRate(){
        Map<String, Double> measureMap = new HashMap<>();
        measureMap.put("Prosecutor_attacker_success_rate",1.0);
        measureMap.put("Journalist_attacker_success_rate",1.0);
        measureMap.put("Marketer_attacker_success_rate",1.0);
        return new AttackerSuccess(measureMap);
    }

    public static AttributeRisk attributeRisk(){
        ARXPopulationModel pModel = ARXPopulationModel.create(ageGenderZipcodeDataset().getHandle().getNumRows(), 0.01d);
        return AttributeRisk.create(ageGenderZipcodeDataset().getHandle(),pModel);
    }

    public static RiskProfile zipcodeAnalyzationAfterAnonymization(){
        return new RiskProfile(ageGenderZipcodeReIndenticationRiskAfterAnonymization(),ageGenderZipcodeDistributionOfRiskAfterAnonymization(), attributeRiskAfterAnonymization());
    }

    public static AttributeRisk attributeRiskAfterAnonymization(){
        ARXPopulationModel pModel = ARXPopulationModel.create(ageGenderZipcodeDatasetAfterAnonymziation().getHandle().getNumRows(), 0.01d);
        return AttributeRisk.create(ageGenderZipcodeDatasetAfterAnonymziation().getHandle(), pModel);
    }

    public static Request zipcodeRequestPayloadAfterAnonymization(){
        List<String[]> testData = ageGenderZipcodeDataAfterAnonymization();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(null);
        Double suppressionLimit = 0.02;
        return new Request(testData, testAttributes, null,suppressionLimit);
    }

    private static Data ageGenderZipcodeDatasetAfterAnonymziation(){
        DataFactory datafactory = new ARXDataFactory();
        return datafactory.create(zipcodeRequestPayloadAfterAnonymization());
    }

    private static DistributionOfRisk ageGenderZipcodeDistributionOfRiskAfterAnonymization(){
        return DistributionOfRisk.create(ageGenderZipcodeDatasetAfterAnonymziation().getHandle().getRiskEstimator());
    }

    private static ReIdentificationRisk ageGenderZipcodeReIndenticationRiskAfterAnonymization(){
        return new ReIdentificationRisk(ageGenderZipcodeMeasuresAfterAnonymization(), attackerSuccessRate(), quasiIdentifiers(), populationModelAfterAnon());
    }

    private static Map<String, Double> ageGenderZipcodeMeasuresAfterAnonymization() {
        Map<String, Double> expected = new HashMap<>();
        expected.put("records_affected_by_highest_prosecutor_risk",1.0);
        expected.put("sample_uniques",0.0);
        expected.put("estimated_prosecutor_risk",0.09090909090909091);
        expected.put("highest_journalist_risk",0.09090909090909091);
        expected.put("records_affected_by_lowest_risk",1.0);
        expected.put("estimated_marketer_risk",0.09090909090909091);
        expected.put("highest_prosecutor_risk",0.09090909090909091);
        expected.put("estimated_journalist_risk",0.09090909090909091);
        expected.put("lowest_risk",0.09090909090909091);
        expected.put("average_prosecutor_risk",0.09090909090909091);
        expected.put("records_affected_by_highest_journalist_risk",1.0);
        expected.put("population_uniques",0.0);
        return expected;
    }

    private static String populationModelAfterAnon(){
        return "DANKAR";
    }

    private static AttackerSuccess attackerSuccessRateAfterAnon(){
        Map<String,Double> expected = new HashMap<>();

        expected.put("Journalist_attacker_success_rate",0.09090909090909092);
        expected.put("Marketer_attacker_success_rate",0.09090909090909092);
        expected.put("Prosecutor_attacker_success_rate",0.09090909090909092);
        return new AttackerSuccess(expected);
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
        ARXConfiguration config = configurationFactory.create(zipcodeRequestPayload().getPrivacyModels(),zipcodeRequestPayload().getSuppressionLimit());
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

    public static String testFormData_metadata_two_quasi(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":0}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":5}}]," +
                "\"suppressionLimit\":0.02}";
    }

    public static String testFormData_metadata_two_quasi_with_hierarchies(){
        return "{\"attributes\":[{\"field\":\"age\",\"attributeTypeModel\":\"IDENTIFYING\",\"hierarchy\":null}," +
                "{\"field\":\"gender\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":0}," +
                "{\"field\":\"zipcode\",\"attributeTypeModel\":\"QUASIIDENTIFYING\",\"hierarchy\":1}]," +
                "\"privacyModels\":[{\"privacyModel\":\"KANONYMITY\",\"params\":{\"k\":2}}]," +
                "\"suppressionLimit\":0.02}";
    }

}
