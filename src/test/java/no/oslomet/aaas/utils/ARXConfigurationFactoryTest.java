package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.DistinctLDiversity;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static no.oslomet.aaas.model.AttributeTypeModel.*;
import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY_DISTINCT;
import static org.deidentifier.arx.AttributeType.*;
import static org.junit.jupiter.api.Assertions.*;

class ARXConfigurationFactoryTest {

    private Data.DefaultData testData = Data.create();
    private MetaData testMetaData;

    @BeforeEach
    private void generateTestData() {
        testData.add("age", "gender", "zipcode");
        testData.add("34", "male", "81667");
        testData.add("35", "female", "81668");
        testData.add("36", "male", "81669");
        testData.add("37", "female", "81670");
        testData.add("38", "male", "81671");
        testData.add("39", "female", "81672");
        testData.add("40", "male", "81673");
        testData.add("41", "female", "81674");
        testData.add("42", "male", "81675");
        testData.add("43", "female", "81676");
        testData.add("44", "male", "81677");

        testData.getDefinition().setAttributeType("age",IDENTIFYING_ATTRIBUTE);
        testData.getDefinition().setAttributeType("gender",SENSITIVE_ATTRIBUTE);
        testData.getDefinition().setAttributeType("zipcode",QUASI_IDENTIFYING_ATTRIBUTE);

        testMetaData = new MetaData();

        //Define K-anonymity
        Map<PrivacyModel,Map<String,String>> testMapPrivacy = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMapPrivacy.put(KANONYMITY,testMapValue);

        //Define L-distinct diversity
        Map<String,String> testMapLValue = new HashMap<>();
        testMapLValue.put("l","3");
        testMapLValue.put("column_name","gender");
        testMapPrivacy.put(LDIVERSITY_DISTINCT,testMapLValue);
        testMetaData.setModels(testMapPrivacy);
    }

    @Test
    void create_NotNull() {
        ARXConfigurationFactory arxConfigurationFactory = new ARXConfigurationFactory(new PrivacyModelFactory());
        ARXConfiguration resultConfig = arxConfigurationFactory.create(testMetaData);
        Assertions.assertNotNull(resultConfig);
    }
}