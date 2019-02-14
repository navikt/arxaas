package no.OsloMET.aaas.utils;

import no.oslomet.aaas.utils.ARXResponseAnalyser;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ARXResponseAnalyserTest {

    ARXResponseAnalyser arxResponseAnalyser;

    @Before
    public void initilze(){
        arxResponseAnalyser = new ARXResponseAnalyser();
    }

    //----------------Preparing test Data -------------------------//
    Data.DefaultData data = Data.create();

    ARXConfiguration config = ARXConfiguration.create();

    ARXAnonymizer anonymizer = new ARXAnonymizer();

    ARXResult AnonymizeResult;

    ARXPopulationModel pModel;

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

        pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
    }
    //--------------------------------------------------------

    @Test
    public void getLowestProsecutorRisk() {
        String  actual = String.valueOf(arxResponseAnalyser.getLowestProsecutorRisk(AnonymizeResult,pModel));
        String expected = "0.16666666666666666";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getRecordsAffectByRisk() {
        double testRisk = arxResponseAnalyser.getLowestProsecutorRisk(AnonymizeResult,pModel);
        String actual = String.valueOf(arxResponseAnalyser.getRecordsAffectByRisk(AnonymizeResult,pModel,testRisk));
        String expected ="0.5454545454545454";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAverageProsecutorRisk() {
        String actual = String.valueOf(arxResponseAnalyser.getAverageProsecutorRisk(AnonymizeResult,pModel));
        String expected = "0.18181818181818182";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getHighestProsecutorRisk() {
        String actual = String.valueOf(arxResponseAnalyser.getHighestProsecutorRisk(AnonymizeResult,pModel));
        String expected = "0.2";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getEstimatedProsecutorRisk() {
        String actual=String.valueOf(arxResponseAnalyser.getEstimatedProsecutorRisk(AnonymizeResult,pModel));
        String expected ="0.2";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getEstimatedJournalistRisk() {

    }

    @Test
    public void getEstimatedMarketerRisk() {
    }

    @Test
    public void getSampleUniques() {
    }

    @Test
    public void getPopulationUniques() {
    }

    @Test
    public void getPopulationModel() {
    }

    @Test
    public void getQuasiIdentifiers() {
    }

    @Test
    public void showAnalysisData() {
    }
}