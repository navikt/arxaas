package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.anonymity.PrivacyCriterionModel;
import org.deidentifier.arx.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class ARXPrivacyCriterionFactoryTest {

    private Map<PrivacyCriterionModel.PrivacyModel, Map<String, String>> models;

    @BeforeEach
    void setUp(){
        models = new HashMap<>();
        models.put(PrivacyCriterionModel.PrivacyModel.KANONYMITY, Map.of("k","4","expected",new KAnonymity(4).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_DISTINCT, Map.of("l","4","column_name","gender",
                "expected",new DistinctLDiversity("gender",4).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_SHANNONENTROPY,Map.of("l","4","column_name","gender",
                "expected",new EntropyLDiversity("gender",4,EntropyLDiversity.EntropyEstimator.SHANNON).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_GRASSBERGERENTROPY,Map.of("l","4","column_name","gender",
                "expected",new EntropyLDiversity("gender",4,EntropyLDiversity.EntropyEstimator.GRASSBERGER).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_RECURSIVE, Map.of("l","4","c","2","column_name","gender",
                "expected", new RecursiveCLDiversity("gender", 4,2).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.TCLOSENESS_EQUAL_DISTANCE,Map.of("t","0.3","column_name", "gender",
                "expected", new EqualDistanceTCloseness("gender", 0.3).toString()));
        models.put(PrivacyCriterionModel.PrivacyModel.TCLOSENESS_ORDERED_DISTANCE,Map.of("t","0.3","column_name", "gender",
                "expected", new OrderedDistanceTCloseness("gender", 0.3).toString()));

    }

    @Test
    void create_returnObject_is_Correct() {

        ARXPrivacyCriterionFactory factory = new ARXPrivacyCriterionFactory();
        for (Map.Entry<PrivacyCriterionModel.PrivacyModel, Map<String,String>> entry : models.entrySet())
        {
            PrivacyCriterion result = factory.create(entry.getKey(),entry.getValue());
            String actual = result.toString();
            String expected = entry.getValue().get("expected");
            Assertions.assertEquals(expected, actual);
        }

    }

}