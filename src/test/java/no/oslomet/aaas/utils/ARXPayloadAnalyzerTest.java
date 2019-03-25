package no.oslomet.aaas.utils;

import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.*;


public class ARXPayloadAnalyzerTest {

    private ARXPayloadAnalyser arxPayloadAnalyser;

    @Before
    public void initialize(){
        arxPayloadAnalyser = new ARXPayloadAnalyser();
    }

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
        double  actual = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0, actual);
    }

    @Test
    public void getPayloadRecordsAffectByRisk() {
        double testRisk = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel);
        double actual = arxPayloadAnalyser.getPayloadRecordsAffectByRisk(testData,pModel,testRisk);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadAverageProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadAverageProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadHighestProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadHighestProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedJournalistRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedJournalistRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadEstimatedMarketerRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedMarketerRisk(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadSampleUniques() {
        double actual = arxPayloadAnalyser.getPayloadSampleUniques(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadPopulationUniques() {
        double actual = arxPayloadAnalyser.getPayloadPopulationUniques(testData,pModel);
        Assertions.assertEquals(1.0,actual);
    }

    @Test
    public void getPayloadPopulationModel() {
        RiskModelPopulationUniqueness.PopulationUniquenessModel actual = arxPayloadAnalyser.getPayloadPopulationModel(testData,pModel);
        Assertions.assertEquals(RiskModelPopulationUniqueness.PopulationUniquenessModel.ZAYATZ,actual);
    }

    @Test
    public void getPayloadQuasiIdentifiers() {
        Set<String> actual = arxPayloadAnalyser.getPayloadQuasiIdentifiers(testData);
        Set<String> expected = Set.of("zipcode", "gender");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getPayloadDistributionOfRecordsWithRisk(){
        double[] actual = arxPayloadAnalyser.getPayloadDistributionOfRecordsWithRisk(testData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getPayloadDistributionOfRecordsWithMaximalRisk(){
        double[] actual = arxPayloadAnalyser.getPayloadDistributionOfRecordsWithMaximalRisk(testData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getPayloadAnalysisData() {
        Map<String, String> actual = arxPayloadAnalyser.getPayloadAnalysisData(testData,pModel);
        Map<String,String > expected = new HashMap<>();
                expected.put("measure_value","[%]");
                expected.put("records_affected_by_highest_risk","100.0");
                expected.put("sample_uniques","100.0");
                expected.put("estimated_prosecutor_risk","100.0");
                expected.put("population_model","ZAYATZ");
                expected.put("records_affected_by_lowest_risk","100.0");
                expected.put("estimated_marketer_risk","100.0");
                expected.put("highest_prosecutor_risk","100.0");
                expected.put("estimated_journalist_risk","100.0");
                expected.put("lowest_risk","100.0");
                expected.put("average_prosecutor_risk","100.0");
                expected.put("population_uniques","100.0");
                expected.put("quasi_identifiers","[zipcode, gender]");

        Assertions.assertEquals(expected,actual);
    }

    //-------------------------Test against re-identification risk for dataset after anonymisation----------------//
    @Test
    public void getLowestProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.16666666666666666,actual);
    }

    @Test
    public void getRecordsAffectByRisk() {
        double testRisk = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel);
        double actual = arxPayloadAnalyser.getPayloadRecordsAffectByRisk(testResultData,pModel,testRisk);
        Assertions.assertEquals(0.5454545454545454,actual);
    }

    @Test
    public void getAverageProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadAverageProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getHighestProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadHighestProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedProsecutorRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedJournalistRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedJournalistRisk(testResultData,pModel);
        Assertions.assertEquals(0.2,actual);
    }

    @Test
    public void getEstimatedMarketerRisk() {
        double actual = arxPayloadAnalyser.getPayloadEstimatedMarketerRisk(testResultData,pModel);
        Assertions.assertEquals(0.18181818181818182,actual);
    }

    @Test
    public void getSampleUniques() {
        double actual = arxPayloadAnalyser.getPayloadSampleUniques(testResultData,pModel);
        Assertions.assertEquals(0.0,actual);
    }

    @Test
    public void getPopulationUniques() {
        double actual = arxPayloadAnalyser.getPayloadPopulationUniques(testResultData,pModel);
        Assertions.assertEquals(0.0,actual);
    }

    @Test
    public void getPopulationModel() {
        RiskModelPopulationUniqueness.PopulationUniquenessModel actual = arxPayloadAnalyser.getPayloadPopulationModel(testResultData,pModel);
        Assertions.assertEquals(RiskModelPopulationUniqueness.PopulationUniquenessModel.DANKAR,actual);
    }

    @Test
    public void getQuasiIdentifiers() {
        Set<String> actual = arxPayloadAnalyser.getPayloadQuasiIdentifiers(testResultData);
        Set<String> expected = Set.of("zipcode", "gender");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void getDistributionOfRecordsWithRisk(){
        double[] actual = arxPayloadAnalyser.getPayloadDistributionOfRecordsWithRisk(testResultData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,0.45454545454545453,0.0,0.0,0.0,0.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void getDistributionOfRecordsWithMaximalRisk(){
        double[] actual = arxPayloadAnalyser.getPayloadDistributionOfRecordsWithMaximalRisk(testResultData,pModel);
        double[] expected ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,1.0,1.0,1.0,1.0,1.0};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void showAnalysisData() {
        Map<String, String> actual = arxPayloadAnalyser.getPayloadAnalysisData(testResultData,pModel);
        Map<String,String> expected = new HashMap<>();
                expected.put("measure_value","[%]");
                expected.put("records_affected_by_highest_risk","45.45454545454545");
                expected.put("sample_uniques","0.0");
                expected.put("estimated_prosecutor_risk","20.0");
                expected.put("population_model","DANKAR");
                expected.put("records_affected_by_lowest_risk","54.54545454545454");
                expected.put("estimated_marketer_risk","18.181818181818183");
                expected.put("highest_prosecutor_risk","20.0");
                expected.put("estimated_journalist_risk","20.0");
                expected.put("lowest_risk","16.666666666666664");
                expected.put("average_prosecutor_risk","18.181818181818183");
                expected.put("population_uniques","0.0");
                expected.put("quasi_identifiers","[zipcode, gender]");

        Assert.assertEquals(expected,actual);
    }
}