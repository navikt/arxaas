package no.oslomet.aaas.utils;

import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.*;


public class ARXPayloadAnalyzerTest {

    //----------------Preparing test Data -------------------------//
    private Data.DefaultData data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
    private ARXAnonymizer anonymizer = new ARXAnonymizer();
    private ARXResult anonymizeResult;
    private DataHandle testData;
    private DataHandle testResultData;
    private ARXPopulationModel pModel;

    @Before
    public void generateTestData() {
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

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        data.getDefinition().setAttributeType("age", AttributeType.IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("gender", AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("zipcode", AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);

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

        config.addPrivacyModel(new KAnonymity(4));
        config.setSuppressionLimit(0.02d);

        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        try {
            anonymizeResult = anonymizer.anonymize(data,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        testData=data.getHandle();
        testResultData= anonymizeResult.getOutput();

        pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
    }
    //--------------------------------------------------------

    //-------------------------Test against re-identification risk for dataset before anonymisation----------------//
    @Test
    public void getPayloadLowestProsecutorRisk() {
        double  actual = ARXPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0, actual);
    }

    @Test
    public void getPayloadRecordsAffectByRisk() {
        double testRisk = ARXPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel);
        double actual = ARXPayloadAnalyser.getPayloadRecordsAffectByRisk(testData,pModel,testRisk);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadAverageProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadAverageProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadHighestProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadHighestProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPaylaodHighestJournalistRisk(){
        double actual = ARXPayloadAnalyser.getPayloadHighestJournalistRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedJournalistRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedJournalistRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedMarketerRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedMarketerRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadSampleUniques() {
        double actual = ARXPayloadAnalyser.getPayloadSampleUniques(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadPopulationUniques() {
        double actual = ARXPayloadAnalyser.getPayloadPopulationUniques(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadPopulationModel() {
        RiskModelPopulationUniqueness.PopulationUniquenessModel actual = ARXPayloadAnalyser.getPayloadPopulationModel(testData,pModel);
        Assertions.assertEquals(RiskModelPopulationUniqueness.PopulationUniquenessModel.ZAYATZ,actual);
    }

    @Test
    public void getPayloadQuasiIdentifiers() {
        Set<String> actual = ARXPayloadAnalyser.getPayloadQuasiIdentifiers(testData);
        Set<String> expected = Set.of("zipcode", "gender");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getPayloadProsecutorAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadProsecutorAttackSuccessRate(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadJournalistAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadJournalistAttackerSuccessRate(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadMarketerAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadMarketerAttackerSuccessRate(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadDistributionOfRecordsWithRisk(){
        double[] actual = ARXPayloadAnalyser.getPayloadDistributionOfRecordsWithRisk(testData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getPayloadDistributionOfRecordsWithMaximalRisk(){
        double[] actual = ARXPayloadAnalyser.getPayloadDistributionOfRecordsWithMaximalRisk(testData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getPayloadAnalysisData() {
        Map<String, String> actual = ARXPayloadAnalyser.getPayloadAnalyzeData(testData,pModel);
        Map<String,String > expected = new HashMap<>();
                expected.put("measure_value","[%]");
                expected.put("Prosecutor_attacker_success_rate","100.0");
                expected.put("records_affected_by_highest_prosecutor_risk","100.0");
                expected.put("sample_uniques","100.0");
                expected.put("estimated_prosecutor_risk","100.0");
                expected.put("population_model","ZAYATZ");
                expected.put("highest_journalist_risk","100.0");
                expected.put("records_affected_by_lowest_risk","100.0");
                expected.put("estimated_marketer_risk","100.0");
                expected.put("Journalist_attacker_success_rate","100.0");
                expected.put("highest_prosecutor_risk","100.0");
                expected.put("estimated_journalist_risk","100.0");
                expected.put("lowest_risk","100.0");
                expected.put("Marketer_attacker_success_rate","100.0");
                expected.put("average_prosecutor_risk","100.0");
                expected.put("records_affected_by_highest_journalist_risk","100.0");
                expected.put("population_uniques","100.0");
                expected.put("quasi_identifiers","[zipcode, gender]");

        Assertions.assertEquals(expected,actual);
    }

    //-------------------------Test against re-identification risk for dataset after anonymisation----------------//
    @Test
    public void getLowestProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.16666666666666666,actual);
    }

    @Test
    public void getRecordsAffectByRisk() {
        double testRisk = ARXPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel);
        double actual = ARXPayloadAnalyser.getPayloadRecordsAffectByRisk(testResultData,pModel,testRisk);
        Assertions.assertEquals(0.5454545454545454,actual);
    }

    @Test
    public void getAverageProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadAverageProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getHighestProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadHighestProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getHighestJournalistRisk(){
        double actual = ARXPayloadAnalyser.getPayloadHighestJournalistRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedProsecutorRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedJournalistRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedJournalistRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedMarketerRisk() {
        double actual = ARXPayloadAnalyser.getPayloadEstimatedMarketerRisk(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getSampleUniques() {
        double actual = ARXPayloadAnalyser.getPayloadSampleUniques(testResultData,pModel);
        Assertions.assertEquals(0.0,actual);
    }

    @Test
    public void getPopulationUniques() {
        double actual = ARXPayloadAnalyser.getPayloadPopulationUniques(testResultData,pModel);
        Assertions.assertEquals(0.0,actual);
    }

    @Test
    public void getPopulationModel() {
        RiskModelPopulationUniqueness.PopulationUniquenessModel actual = ARXPayloadAnalyser.getPayloadPopulationModel(testResultData,pModel);
        Assertions.assertEquals(RiskModelPopulationUniqueness.PopulationUniquenessModel.DANKAR,actual);
    }

    @Test
    public void getQuasiIdentifiers() {
        Set<String> actual = ARXPayloadAnalyser.getPayloadQuasiIdentifiers(testResultData);
        Set<String> expected = Set.of("zipcode", "gender");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getProsecutorAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadProsecutorAttackSuccessRate(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getJournalistAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadJournalistAttackerSuccessRate(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getMarketerAttackerSuccessRate(){
        double actual = ARXPayloadAnalyser.getPayloadMarketerAttackerSuccessRate(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getDistributionOfRecordsWithRisk(){
        double[] actual = ARXPayloadAnalyser.getPayloadDistributionOfRecordsWithRisk(testResultData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,0.45454545454545453,0.0,0.0,0.0,0.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getDistributionOfRecordsWithMaximalRisk(){
        double[] actual = ARXPayloadAnalyser.getPayloadDistributionOfRecordsWithMaximalRisk(testResultData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,1.0,1.0,1.0,1.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void showAnalysisData() {
        Map<String, String> actual = ARXPayloadAnalyser.getPayloadAnalyzeData(testResultData,pModel);
        Map<String,String> expected = new HashMap<>();
                expected.put("measure_value","[%]");
                expected.put("Prosecutor_attacker_success_rate","18.181818181818183");
                expected.put("records_affected_by_highest_prosecutor_risk","45.45454545454545");
                expected.put("sample_uniques","0.0");
                expected.put("estimated_prosecutor_risk","20.0");
                expected.put("population_model","DANKAR");
                expected.put("highest_journalist_risk","20.0");
                expected.put("records_affected_by_lowest_risk","54.54545454545454");
                expected.put("estimated_marketer_risk","18.181818181818183");
                expected.put("Journalist_attacker_success_rate","18.181818181818183");
                expected.put("highest_prosecutor_risk","20.0");
                expected.put("estimated_journalist_risk","20.0");
                expected.put("lowest_risk","16.666666666666664");
                expected.put("Marketer_attacker_success_rate","18.181818181818183");
                expected.put("average_prosecutor_risk","18.181818181818183");
                expected.put("records_affected_by_highest_journalist_risk","45.45454545454545");
                expected.put("population_uniques","0.0");
                expected.put("quasi_identifiers","[zipcode, gender]");

        Assertions.assertEquals(expected,actual);
    }
}