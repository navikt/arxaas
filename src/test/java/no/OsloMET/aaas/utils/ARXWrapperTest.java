package no.OsloMET.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;

public class ARXWrapperTest {
    private ARXWrapper arxWrapper;

    @Before
    public void initialize(){
        arxWrapper = new ARXWrapper();
    }

    //-------------------------preparing test payload----------------------------//
    private Data data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
    private ARXAnonymizer anonymizer = new ARXAnonymizer();
    private AnonymizationPayload testPayload = new AnonymizationPayload();
    private MetaData testMetaData = new MetaData();

    @Before
    public void generateTestData() {
        String testData ="age, gender, zipcode\n" +
                "34, male, 81667\n" +
                "35, female, 81668\n" +
                "36, male, 81669\n" +
                "37, female, 81670\n" +
                "38, male, 81671\n" +
                "39, female, 81672\n" +
                "40, male, 81673\n" +
                "41, female, 81674\n" +
                "42, male, 81675\n" +
                "43, female , 81676\n" +
                "44, male, 81677";

        testPayload.setData(testData);

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String,SensitivityModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setSensitivityList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String ,String[][]> testMapHierarchy = new HashMap<>();
        String [][] testHeirarchy = new String[][]{
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81668", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81669", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81670", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81671", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81672", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81673", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81674", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81675", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81676", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode",testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel,Map<String,String>> testMapPrivacy = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMapPrivacy.put(KANONYMITY,testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
    }
    //------------------------------------------------------------------------//

    @Test
    public void setData() {
        Data result = arxWrapper.setData(testPayload.getData());
        String actualValue1 = result.getHandle().getValue(0,0);
        String actualValue2 = result.getHandle().getValue(1,1);
        String actualValue3 = result.getHandle().getValue(2,2);

        Assert.assertEquals("34",actualValue1);
        Assert.assertEquals("female",actualValue2);
        Assert.assertEquals("81669",actualValue3);
    }

    @Test
    public void setSuppression(){
        arxWrapper.setSuppressionLimit(config);
        String actual = String.valueOf(config.getSuppressionLimit());

        Assert.assertEquals("0.02",actual);
    }

    @Test
    public void setSensitivityModels() {
        arxWrapper.setSensitivityModels(data, testPayload);
        String actual = String.valueOf(data.getDefinition().getAttributeType("age"));

        Assert.assertEquals("IDENTIFYING_ATTRIBUTE",actual);
    }

    @Test
    public void setPrivacyModelsKAnon(){
        arxWrapper.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[5-anonymity]",actual);
    }

    @Test
    public void setPrivacyModelsLDiv(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","age");
        testMapValue.put("variant","distinct");
        testMap.put(LDIVERSITY,testMapValue);
        testMetaData.setModels(testMap);
        testPayload.setMetaData(testMetaData);

        arxWrapper.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[distinct-5-diversity for attribute 'age']",actual);
        }

    @Test
    public void setHierarchies(){
        data = arxWrapper.setHierarchies(data, testPayload);
        String[][] actual = data.getDefinition().getHierarchy("zipcode");
        String actualResult1 = actual[0][0];
        String actualResult2 = actual[6][3];
        String actualResult3 = actual[10][5];

        Assert.assertEquals("81667",actualResult1);
        Assert.assertEquals("81***",actualResult2);
        Assert.assertEquals("*****",actualResult3);
    }

    @Test
    public void setAnonymizer() {
        arxWrapper.setAnonymizer(anonymizer);
        String actual1=String.valueOf(anonymizer.getMaximumSnapshotSizeDataset());
        String actual2=String.valueOf(anonymizer.getMaximumSnapshotSizeSnapshot());
        String actual3=String.valueOf(anonymizer.getHistorySize());

        Assert.assertEquals("0.2",actual1);
        Assert.assertEquals("0.2",actual2);
        Assert.assertEquals("200",actual3);
    }

    @Test
    public void anonymize() {
        try {
            String actual = String.valueOf(arxWrapper.anonymize(anonymizer,config, testPayload));
            String expected = "age;gender;zipcode\n" +
                    "*;male;816**\n" +
                    "*;female;816**\n" +
                    "*;male;816**\n" +
                    "*;female;816**\n" +
                    "*;male;816**\n" +
                    "*;female;816**\n" +
                    "*;male;816**\n" +
                    "*;female;816**\n" +
                    "*;male;816**\n" +
                    "*;female;816**\n" +
                    "*;male;816**\n";
            Assert.assertEquals(expected,actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}