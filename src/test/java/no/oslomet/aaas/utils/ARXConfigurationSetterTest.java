package no.oslomet.aaas.utils;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.*;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY_RECURSIVE;

public class ARXConfigurationSetterTest {

    private ARXConfigurationSetter arxConfigurationSetter;

    @Before
    public void initialize(){
        arxConfigurationSetter = new ARXConfigurationSetter();
    }

    private Data data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
    private AnonymizationPayload testPayload = new AnonymizationPayload();

    @Before
    public void generateTestData() {
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }


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
        testMapValue.put("column_name","gender");
        testMap.put(LDIVERSITY_DISTINCT,testMapValue);
        testPayload.getMetaData().setModels(testMap);
        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[distinct-5-diversity for attribute 'gender']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_SHANNONENTROPY(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","gender");
        testMap.put(LDIVERSITY_SHANNONENTROPY,testMapValue);
        testPayload.getMetaData().setModels(testMap);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[shannon-entropy-5.0-diversity for attribute 'gender']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_GRASSBERGERENTROPY(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("column_name","gender");
        testMap.put(LDIVERSITY_GRASSBERGERENTROPY,testMapValue);
        testPayload.getMetaData().setModels(testMap);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[grassberger-entropy-5.0-diversity for attribute 'gender']",actual);
    }

    @Test
    public void setPrivacyModels_LDIVERSITY_RECURSIVE(){
        data.getDefinition().setAttributeType("age", AttributeType.SENSITIVE_ATTRIBUTE);
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("l","5");
        testMapValue.put("c","3");
        testMapValue.put("column_name","gender");
        testMap.put(LDIVERSITY_RECURSIVE,testMapValue);
        testPayload.getMetaData().setModels(testMap);

        arxConfigurationSetter.setPrivacyModels(config, testPayload);
        String actual = String.valueOf(config.getPrivacyModels());

        Assert.assertEquals("[recursive-(5.0,3)-diversity for attribute 'gender']",actual);
    }
}
