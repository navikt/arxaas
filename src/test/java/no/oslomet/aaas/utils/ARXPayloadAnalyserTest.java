package no.oslomet.aaas.utils;

import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class ARXPayloadAnalyserTest {

    private ARXPayloadAnalyser arxPayloadAnalyser;

    @Before
    public void initialize(){
        arxPayloadAnalyser = new ARXPayloadAnalyser();
    }

    //----------------Preparing test Data -------------------------//
    private Data.DefaultData data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
    private ARXAnonymizer anonymizer = new ARXAnonymizer();
    private ARXResult AnonymizeResult;
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
            AnonymizeResult = anonymizer.anonymize(data,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        testData=data.getHandle();
        testResultData=AnonymizeResult.getOutput();

        pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
    }
    //--------------------------------------------------------

    //-------------------------Test against re-identification risk for dataset before anonymisation----------------//
    @Test
    public void getPayloadLowestProsecutorRisk() {
        String  actual = String.valueOf(arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadRecordsAffectByRisk() {
        double testRisk = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testData,pModel);
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadRecordsAffectByRisk(testData,pModel,testRisk));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadAverageProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadAverageProsecutorRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadHighestProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadHighestProsecutorRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedJournalistRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedJournalistRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedMarketerRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedMarketerRisk(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadSampleUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadSampleUniques(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadPopulationUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationUniques(testData,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadPopulationModel() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationModel(testData,pModel));
        Assert.assertEquals("ZAYATZ",actual);
    }

    @Test
    public void getPayloadQuasiIdentifiers() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadQuasiIdentifiers(testData));
        Assert.assertEquals("[zipcode, gender]",actual);
    }

    @Test
    public void getPayloadAnalysisData() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadAnalysisData(testData,pModel));
        String expected ="{measure_value=[%], " +
                "records_affected_by_highest_risk=100.0, " +
                "sample_uniques=100.0, estimated_prosecutor_risk=100.0, " +
                "population_model=ZAYATZ, " +
                "records_affected_by_lowest_risk=100.0, " +
                "estimated_marketer_risk=100.0, " +
                "highest_prosecutor_risk=100.0, " +
                "estimated_journalist_risk=100.0, " +
                "lowest_risk=100.0, " +
                "average_prosecutor_risk=100.0, " +
                "population_uniques=100.0, " +
                "quasi_identifiers=[zipcode, gender]}";
        Assert.assertEquals(expected,actual);
    }

    //-------------------------Test against re-identification risk for dataset after anonymisation----------------//
    @Test
    public void getLowestProsecutorRisk() {
        String  actual = String.valueOf(arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel));
        Assert.assertEquals("0.16666666666666666",actual);
    }

    @Test
    public void getRecordsAffectByRisk() {
        double testRisk = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(testResultData,pModel);
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadRecordsAffectByRisk(testResultData,pModel,testRisk));
        Assert.assertEquals("0.5454545454545454",actual);
    }

    @Test
    public void getAverageProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadAverageProsecutorRisk(testResultData,pModel));
        Assert.assertEquals("0.18181818181818182",actual);
    }

    @Test
    public void getHighestProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadHighestProsecutorRisk(testResultData,pModel));
        Assert.assertEquals("0.2",actual);
    }

    @Test
    public void getEstimatedProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedProsecutorRisk(testResultData,pModel));
        Assert.assertEquals("0.2",actual);
    }

    @Test
    public void getEstimatedJournalistRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedJournalistRisk(testResultData,pModel));
        Assert.assertEquals("0.2",actual);
    }

    @Test
    public void getEstimatedMarketerRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedMarketerRisk(testResultData,pModel));
        Assert.assertEquals("0.18181818181818182",actual);
    }

    @Test
    public void getSampleUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadSampleUniques(testResultData,pModel));
        Assert.assertEquals("0.0",actual);
    }

    @Test
    public void getPopulationUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationUniques(testResultData,pModel));
        Assert.assertEquals("0.0",actual);
    }

    @Test
    public void getPopulationModel() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationModel(testResultData,pModel));
        Assert.assertEquals("DANKAR",actual);
    }

    @Test
    public void getQuasiIdentifiers() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadQuasiIdentifiers(testResultData));
        Assert.assertEquals("[zipcode, gender]",actual);
    }

    @Test
    public void showAnalysisData() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadAnalysisData(testResultData,pModel));
        String expected ="{measure_value=[%], " +
                "records_affected_by_highest_risk=45.45454545454545, " +
                "sample_uniques=0.0, " +
                "estimated_prosecutor_risk=20.0, " +
                "population_model=DANKAR, " +
                "records_affected_by_lowest_risk=54.54545454545454, " +
                "estimated_marketer_risk=18.181818181818183, " +
                "highest_prosecutor_risk=20.0, " +
                "estimated_journalist_risk=20.0, " +
                "lowest_risk=16.666666666666664, " +
                "average_prosecutor_risk=18.181818181818183, " +
                "population_uniques=0.0, " +
                "quasi_identifiers=[zipcode, gender]}";

        Assert.assertEquals(expected,actual);
    }
}