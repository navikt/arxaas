package no.nav.arxaas.arx;

import org.deidentifier.arx.*;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

class HierarchyBuilderRedactionBasedTest {


    private Data.DefaultData testData;

    @BeforeEach
    void createExamleDefaultData(){
        Data.DefaultData data = Data.create();
        data.add("id", "name");

        for (int i=100; i< 200; i++){
            data.add(String.valueOf(i), i + "Name");
        }
        testData = data;
    }

    @Test
    void redactionBasedHierachyGeneration() throws IOException {

        // Create the builder
        HierarchyBuilderRedactionBased<?> builder = HierarchyBuilderRedactionBased.create(HierarchyBuilderRedactionBased.Order.RIGHT_TO_LEFT,
                HierarchyBuilderRedactionBased.Order.RIGHT_TO_LEFT,
                ' ', '*');

        testData.getDefinition().setAttributeType("id", builder);
        testData.getDefinition().setAttributeType("name", AttributeType.INSENSITIVE_ATTRIBUTE);


        // Create config
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0d);
        ARXResult result = anonymizer.anonymize(testData, config);


        var handle = result.getOutput();

        var coll = new ArrayList<String[]>();
        handle.iterator().forEachRemaining(coll::add);


        Assertions.assertEquals(ARXLattice.Anonymity.ANONYMOUS, result.getGlobalOptimum().getAnonymity());

        Assertions.assertEquals("id", coll.get(0)[0]);
        Assertions.assertEquals("name", coll.get(0)[1]);
        Assertions.assertEquals("19*", coll.get(99)[0]);
        Assertions.assertEquals("198Name", coll.get(99)[1]);

    }
}
