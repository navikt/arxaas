package no.OsloMET.aaas.utils;

import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import org.deidentifier.arx.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ARXPayloadAnalyserTest {

    private ARXPayloadAnalyser arxPayloadAnalyser;

    @Before
    public void initialize(){
        arxPayloadAnalyser = new ARXPayloadAnalyser();
    }

    //----------------Preparing test Data -------------------------//
    private Data.DefaultData data = Data.create();
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

        pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
    }
    //--------------------------------------------------------

    @Test
    public void getPayloadLowestProsecutorRisk() {
        String  actual = String.valueOf(arxPayloadAnalyser.getPayloadLowestProsecutorRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadRecordsAffectByRisk() {
        double testRisk = arxPayloadAnalyser.getPayloadLowestProsecutorRisk(data,pModel);
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadRecordsAffectByRisk(data,pModel,testRisk));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadAverageProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadAverageProsecutorRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadHighestProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadHighestProsecutorRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedProsecutorRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedProsecutorRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedJournalistRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedJournalistRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadEstimatedMarketerRisk() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadEstimatedMarketerRisk(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadSampleUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadSampleUniques(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadPopulationUniques() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationUniques(data,pModel));
        Assert.assertEquals("1.0",actual);
    }

    @Test
    public void getPayloadPopulationModel() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadPopulationModel(data,pModel));
        Assert.assertEquals("ZAYATZ",actual);
    }

    @Test
    public void getPayloadQuasiIdentifiers() {
        String actual = String.valueOf(arxPayloadAnalyser.getPayloadQuasiIdentifiers(data));
        Assert.assertEquals("[zipcode, gender]",actual);
    }

    @Test
    public void showPayloadAnalysisData() {
        String actual = arxPayloadAnalyser.getPayloadAnalysisData(data,pModel);
        String expected = "Measure: Value;[%]\n" +
                "Lowest risk;100.0%\n" +
                "Records affected by lowest risk;100.0%\n" +
                "Average prosecutor risk;100.0%\n" +
                "Highest prosecutor risk;100.0%\n" +
                "Record affected by highest risk;100.0%\n" +
                "Estimated prosecutor risk;100.0%\n" +
                "Estimated prosecutor risk;100.0%\n" +
                "Estimated journalist risk;100.0%\n" +
                "Estimated marketer risk;100.0%\n" +
                "Sample uniques: 100.0%\n" +
                "Population uniques: 100.0%\n" +
                "Population model: ZAYATZ\n" +
                "Quasi-identifiers: [zipcode, gender]\n";
        Assert.assertEquals(expected,actual);
    }
}