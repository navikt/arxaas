package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.IDENTIFYING;
import static no.oslomet.aaas.model.AttributeTypeModel.QUASIIDENTIFYING;
import static no.oslomet.aaas.model.PrivacyModel.*;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY_RECURSIVE;

public class ARXConfigurationSetterTest {

    private ARXConfigurationSetter arxConfigurationSetter;

    @Before
    public void initialize(){
        arxConfigurationSetter = new ARXConfigurationSetter();
    }

    //-------------------------preparing test payload----------------------------//
    private Data data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
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
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setAttributeTypeList(testMapAttribute);

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
    public void setSuppression(){
        arxConfigurationSetter.setSuppressionLimit(config);
        String actual = String.valueOf(config.getSuppressionLimit());

        Assert.assertEquals("0.02",actual);
    }

    @Test
    public void setPrivacyModels_KAnon(){
        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[5-anonymity]",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_DISTINCT(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel, Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","age");
        testMap.put(LDIVERSITY_DISTINCT,testMapValue);
        testMetaData.setModels(testMap);
        testPayload.setMetaData(testMetaData);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[distinct-5-diversity for attribute 'age']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_SHANNONENTROPY(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","age");
        testMap.put(LDIVERSITY_SHANNONENTROPY,testMapValue);
        testMetaData.setModels(testMap);
        testPayload.setMetaData(testMetaData);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[shannon-entropy-5.0-diversity for attribute 'age']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_GRASSBERGERENTROPY(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","age");
        testMap.put(LDIVERSITY_GRASSBERGERENTROPY,testMapValue);
        testMetaData.setModels(testMap);
        testPayload.setMetaData(testMetaData);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[grassberger-entropy-5.0-diversity for attribute 'age']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_RECURSIVE(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("c","3");
        testMapValue.put("column_name","age");
        testMap.put(LDIVERSITY_RECURSIVE,testMapValue);
        testMetaData.setModels(testMap);
        testPayload.setMetaData(testMetaData);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[recursive-(5.0,3)-diversity for attribute 'age']",actual);
    }
}
