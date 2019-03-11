package no.oslomet.aaas.arx;


import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.risk.RiskEstimateBuilder;
import org.deidentifier.arx.risk.RiskModelAttributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnalyzeAttributesTest {

    private Data.DefaultData testData;


    @BeforeEach
    void createTestData(){
        testData = Data.create();
        testData.add("age", "gender", "zipcode");
        testData.add("45", "female", "81675");
        testData.add("34", "male", "81667");
        testData.add("66", "male", "81925");
        testData.add("70", "female", "81931");
        testData.add("34", "female", "81931");
        testData.add("70", "male", "81931");
        testData.add("45", "male", "81931");

        // Define hierarchies
        AttributeType.Hierarchy.DefaultHierarchy age = AttributeType.Hierarchy.create();
        age.add("34", "<50", "*");
        age.add("45", "<50", "*");
        age.add("66", ">=50", "*");
        age.add("70", ">=50", "*");

        AttributeType.Hierarchy.DefaultHierarchy gender = AttributeType.Hierarchy.create();
        gender.add("male", "*");
        gender.add("female", "*");

        // Only excerpts for readability
        AttributeType.Hierarchy.DefaultHierarchy zipcode = AttributeType.Hierarchy.create();
        zipcode.add("81667", "8166*", "816**", "81***", "8****", "*****");
        zipcode.add("81675", "8167*", "816**", "81***", "8****", "*****");
        zipcode.add("81925", "8192*", "819**", "81***", "8****", "*****");
        zipcode.add("81931", "8193*", "819**", "81***", "8****", "*****");

        testData.getDefinition().setAttributeType("age", age);
        testData.getDefinition().setAttributeType("gender", gender);
        testData.getDefinition().setAttributeType("zipcode", zipcode);


    }


    @Test
    void generateAttributeAnalytics(){
        /*
        Test generation of analytics for dataset attributes(columns/field) before doing anonymization


           Region.USA
         * Distinction: 0.2857142857142857, Separation: 0.5714285714285714, Identifier: [gender]
         * Distinction: 0.5714285714285714, Separation: 0.7142857142857143, Identifier: [zipcode]
         * Distinction: 0.5714285714285714, Separation: 0.8571428571428571, Identifier: [age]
         * Distinction: 0.7142857142857143, Separation: 0.9047619047619048, Identifier: [gender, zipcode]
         * Distinction: 0.8571428571428571, Separation: 0.9523809523809523, Identifier: [age, zipcode]
         * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender]
         * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender, zipcode]
         *
         *
        Region.EUROPE
        * Distinction: 0.2857142857142857, Separation: 0.5714285714285714, Identifier: [gender]
        * Distinction: 0.5714285714285714, Separation: 0.7142857142857143, Identifier: [zipcode]
        * Distinction: 0.5714285714285714, Separation: 0.8571428571428571, Identifier: [age]
        * Distinction: 0.7142857142857143, Separation: 0.9047619047619048, Identifier: [gender, zipcode]
        * Distinction: 0.8571428571428571, Separation: 0.9523809523809523, Identifier: [age, zipcode]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender, zipcode]
        *
        *
        Region.EUROPEAN_UNION
        * Distinction: 0.2857142857142857, Separation: 0.5714285714285714, Identifier: [gender]
        * Distinction: 0.5714285714285714, Separation: 0.7142857142857143, Identifier: [zipcode]
        * Distinction: 0.5714285714285714, Separation: 0.8571428571428571, Identifier: [age]
        * Distinction: 0.7142857142857143, Separation: 0.9047619047619048, Identifier: [gender, zipcode]
        * Distinction: 0.8571428571428571, Separation: 0.9523809523809523, Identifier: [age, zipcode]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender, zipcode]
        *
        *
        No Population model
        * Distinction: 0.2857142857142857, Separation: 0.5714285714285714, Identifier: [gender]
        * Distinction: 0.5714285714285714, Separation: 0.7142857142857143, Identifier: [zipcode]
        * Distinction: 0.5714285714285714, Separation: 0.8571428571428571, Identifier: [age]
        * Distinction: 0.7142857142857143, Separation: 0.9047619047619048, Identifier: [gender, zipcode]
        * Distinction: 0.8571428571428571, Separation: 0.9523809523809523, Identifier: [age, zipcode]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender]
        * Distinction: 1.0, Separation: 1.0, Identifier: [age, gender, zipcode]


         */

        DataHandle handle = testData.getHandle();

        ARXPopulationModel populationmodel = ARXPopulationModel.create(ARXPopulationModel.Region.EUROPEAN_UNION);
        RiskEstimateBuilder builder = handle.getRiskEstimator();
        RiskModelAttributes riskmodel = builder.getAttributeRisks();

        List<Map<String, String>> riskList = new ArrayList<Map<String, String>>();


        for (RiskModelAttributes.QuasiIdentifierRisk risk : riskmodel.getAttributeRisks()) {
            Map<String, String> riskMap = new HashMap<>();
            riskMap.put("Distinction", String.valueOf(risk.getDistinction()));
            riskMap.put("Separation", String.valueOf(risk.getSeparation()));
            riskMap.put("Identifier", String.valueOf(risk.getIdentifier()));
            riskList.add(riskMap);
        }

        Assertions.assertEquals("0.5714285714285714", riskList.get(0).get("Separation"));
        Assertions.assertEquals("[zipcode]", riskList.get(1).get("Identifier"));
        Assertions.assertEquals("0.5714285714285714", riskList.get(2).get("Distinction"));
        Assertions.assertEquals( "[age, gender]", riskList.get(5).get("Identifier"));
    }


}
