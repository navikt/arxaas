package no.OsloMET.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;

public class ARXWrapperTest {
    ARXWrapper arxWrapper;
    String testValues = "age, gender, zipcode\n34, male, 81667\n35, female, 81668\n36, male, 81669";

    @Before
    public void initilze(){
        arxWrapper = new ARXWrapper();
    }

    //-------------------------preparing test payload----------------------------//
    Data.DefaultData data = Data.create();
    ARXConfiguration config = ARXConfiguration.create();
    ARXAnonymizer anonymizer = new ARXAnonymizer();
    ARXResult AnonymizeResult;
    AnonymizationPayload testpayload = new AnonymizationPayload();
    MetaData testMetaData = new MetaData();

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

        testpayload.setData(testData);

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

        testpayload.setMetaData(testMetaData);
    }
    //------------------------------------------------------------------------//

    @Test
    public void makedata() {

        String expectedValue1 = "34";
        String expectedValue2 = "female";
        String expectedValue3 = "81669";

        Data result = arxWrapper.setData(testValues);
        String actualValue1 = result.getHandle().getValue(0,0);
        String actualValue2 = result.getHandle().getValue(1,1);
        String actualValue3 = result.getHandle().getValue(2,2);

        Assert.assertEquals(expectedValue1,actualValue1);
        Assert.assertEquals(expectedValue2,actualValue2);
        Assert.assertEquals(expectedValue3,actualValue3);
    }

    @Test
    public void setSuppression(){
        ARXConfiguration testData = ARXConfiguration.create();
        arxWrapper.setSuppressionLimit(testData);

        String actual = String.valueOf(testData.getSuppressionLimit());

        String expected = "0.02";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setSensitivityModels() {

        Data testData = arxWrapper.setData(testValues);
        AnonymizationPayload testpayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();
        Map<String,SensitivityModel> testMap = new HashMap<>();
        testMap.put("age",IDENTIFYING);
        testMetaData.setSensitivityList(testMap);
        testpayload.setMetaData(testMetaData);

        arxWrapper.setSensitivityModels(testData,testpayload);

        String actual = String.valueOf(testData.getDefinition().getAttributeType("age"));
        String expected = "IDENTIFYING_ATTRIBUTE";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setPrivacyModelsKAnon(){

        ARXConfiguration testConfig = ARXConfiguration.create();
        AnonymizationPayload testpayload1 = new AnonymizationPayload();
        MetaData testMetaData1 = new MetaData();
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMap.put(KANONYMITY,testMapValue);
        testMetaData1.setModels(testMap);
        testpayload1.setMetaData(testMetaData);

        arxWrapper.setPrivacyModels(testConfig,testpayload);

        String actual = String.valueOf(testConfig.getPrivacyModels());
        String expected = "[5-anonymity]";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setPrivacyModelsLDiv(){

        Data testData = arxWrapper.setData(testValues);
        ARXConfiguration testConfig = ARXConfiguration.create();
        testData.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        AnonymizationPayload testpayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","age");
        testMapValue.put("variant","distinct");
        testMap.put(LDIVERSITY,testMapValue);
        testMetaData.setModels(testMap);
        testpayload.setMetaData(testMetaData);

        arxWrapper.setPrivacyModels(testConfig,testpayload);

        String actual = String.valueOf(testConfig.getPrivacyModels());
        String expected = "[distinct-5-diversity for attribute 'age']";
        Assert.assertEquals(expected,actual);
        }

    @Test
    public void setHierarchies(){

        Data testData = arxWrapper.setData(testValues);
        AnonymizationPayload testpayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();
        Map<String ,String[][]> testMap = new HashMap<>();
        String [][] testHeirarchy = new String[][]{
                {"81667","81*67"},{"81668","8*668"},{"81669","8166*"}
        };
        testMap.put("zipcode",testHeirarchy);
        testMetaData.setHierarchy(testMap);
        testpayload.setMetaData(testMetaData);

        testData = arxWrapper.setHierarchies(testData,testpayload);

        String actual = Arrays.deepToString(testData.getDefinition().getHierarchy("zipcode"));
        String expected = "[[81667, 81*67], [81668, 8*668], [81669, 8166*]]";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setAnonymizer() {
        arxWrapper.setAnonymizer(anonymizer);
        String actual1=String.valueOf(anonymizer.getMaximumSnapshotSizeDataset());
        String actual2=String.valueOf(anonymizer.getMaximumSnapshotSizeSnapshot());
        String actual3=String.valueOf(anonymizer.getHistorySize());

        String expected1="0.2";
        String expected2="0.2";
        String expected3="200";

        Assert.assertEquals(expected1,actual1);
        Assert.assertEquals(expected2,actual2);
        Assert.assertEquals(expected3,actual3);
    }

    @Test
    public void anonymize() {
        try {
            String actual = String.valueOf(arxWrapper.anonymize(anonymizer,config,testpayload));
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