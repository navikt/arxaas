package no.oslomet.aaas.utils;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.criteria.PrivacyCriterion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ARXConfigurationFactoryTest {

    private Request testPayload;
    private final ARXConfigurationFactory arxConfigurationFactory= new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());

    @BeforeEach
    void generateTestData() {
        testPayload = GenerateTestData.zipcodeRequestPayload();
    }


    @Test
    void create_NotNull() {
        ARXConfigurationFactory arxConfigurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        ARXConfiguration resultConfig = arxConfigurationFactory.create(testPayload.getPrivacyModels());
        Assertions.assertNotNull(resultConfig);
    }

    @Test
    void setSuppression(){
        ARXConfiguration config = arxConfigurationFactory.create(testPayload.getPrivacyModels());
        double actual = config.getSuppressionLimit();

        Assertions.assertEquals(0.02,actual);
    }

    @Test
    void setPrivacyModels_KAnon(){
        ARXConfiguration config = arxConfigurationFactory.create(testPayload.getPrivacyModels());
        Set<PrivacyCriterion> actual = config.getPrivacyModels();
        PrivacyCriterion expected = new KAnonymity(5);
        Assertions.assertEquals(expected.toString(), actual.toArray()[0].toString());
    }
}