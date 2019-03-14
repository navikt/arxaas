package no.oslomet.aaas.utils;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.*;
import static no.oslomet.aaas.model.PrivacyModel.LDIVERSITY_RECURSIVE;

class ARXPrivacyModelModelFactoryTest {

    private AnonymizationPayload testpayload;
    private Map<PrivacyModel, Map<String, String>> models;

    @BeforeEach
    void setUp(){
        testpayload = GenerateTestData.zipcodeAnonymizePayload();
        models = new HashMap<>();
        models.put(KANONYMITY, Map.of("k","4","expected",new KAnonymity(4).toString()));
        models.put(LDIVERSITY_DISTINCT, Map.of("l","4","column_name","gender",
                "expected",new DistinctLDiversity("gender",4).toString()));
        models.put(LDIVERSITY_SHANNONENTROPY,Map.of("l","4","column_name","gender",
                "expected",new EntropyLDiversity("gender",4,EntropyLDiversity.EntropyEstimator.SHANNON).toString()));
        models.put(LDIVERSITY_GRASSBERGERENTROPY,Map.of("l","4","column_name","gender",
                "expected",new EntropyLDiversity("gender",4,EntropyLDiversity.EntropyEstimator.GRASSBERGER).toString()));
        models.put(LDIVERSITY_RECURSIVE, Map.of("l","4","c","2","column_name","gender",
                "expected", new RecursiveCLDiversity("gender", 4,2).toString()));

    }

    @Test
    void create_returnObject_is_Correct() {

        ARXPrivacyCriterionFactory factory = new ARXPrivacyCriterionFactory();
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : models.entrySet())
        {
            PrivacyCriterion result = factory.create(entry.getKey(),entry.getValue());
            String actual = result.toString();
            String expected = entry.getValue().get("expected");
            Assertions.assertEquals(expected, actual);
        }

    }

}