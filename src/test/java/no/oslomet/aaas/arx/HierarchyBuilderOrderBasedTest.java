package no.oslomet.aaas.arx;

import org.deidentifier.arx.*;
import org.deidentifier.arx.aggregates.HierarchyBuilderOrderBased;
import org.deidentifier.arx.criteria.KAnonymity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class HierarchyBuilderOrderBasedTest {

    private final String[] testData = getExampleData();

    private static Data.DefaultData getExamleDefaultData(){
        Data.DefaultData data = Data.create();
        data.add("id", "name");

        for (int i=1; i< 100; i++){
            data.add(String.valueOf(i), String.valueOf(i) + "Name");
        }
        return data;
    }

    private static String[] getExampleData(){

        String[] result = new String[100];
        for (int i=0; i< result.length; i++){
            result[i] = String.valueOf(i);
        }
        return result;
    }

    @Test
    public void generateHierarchy() {

        // Create the builder
        HierarchyBuilderOrderBased<Long> builder = HierarchyBuilderOrderBased.create(DataType.INTEGER, false);

        // Define grouping fanouts
        builder.getLevel(0).addGroup(10, DataType.INTEGER.createAggregate().createIntervalFunction());
        builder.getLevel(1).addGroup(2, DataType.INTEGER.createAggregate().createIntervalFunction());
        builder.getLevel(2).addGroup(2, DataType.INTEGER.createAggregate().createIntervalFunction());

        // Alternatively [NOTE from Sondre: Cant get this method to run .addFanout() is not implemented in builder]
        // builder.setAggregateFunction(AggregateFunction.INTERVAL(DataType.INTEGER));
        // builder.getLevel(0).addFanout(10);
        // builder.getLevel(1).addFanout(2);

        // Add the dataset to generate hierarchy from
        builder.prepare(testData);

        //Build Hierarchy
        String[][] result = builder.build().getHierarchy();

        for (String[] row: result) {
            Assertions.assertEquals(5, row.length);
            Assertions.assertEquals("*", row[4]);
        }
    }

    @Test
    public void anonymizeUsingGeneratedHierarchy() throws IOException {

        Data.DefaultData arxData = getExamleDefaultData();


        HierarchyBuilderOrderBased<Long> builder = HierarchyBuilderOrderBased.create(DataType.INTEGER, false);

        // Define grouping fanouts
        builder.getLevel(0).addGroup(2, DataType.INTEGER.createAggregate().createIntervalFunction());
        builder.getLevel(1).addGroup(3, DataType.INTEGER.createAggregate().createIntervalFunction());
        builder.getLevel(2).addGroup(4, DataType.INTEGER.createAggregate().createIntervalFunction());
        builder.getLevel(3).addGroup(5, DataType.INTEGER.createAggregate().createIntervalFunction());

        arxData.getDefinition().setAttributeType("id", builder);
        // Create config
        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0d);

        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = anonymizer.anonymize(arxData, config);

        DataHandle handle = result.getOutput();
        var coll = new ArrayList<String[]>();
        handle.iterator().forEachRemaining(coll::add);

        //Assert first row elements are column headers
        Assertions.assertEquals("id", coll.get(0)[0]);
        Assertions.assertEquals("name", coll.get(0)[1]);
    }

    @Test
    public void anonymizeDefaultDataUsingGeneratedHierarchy() throws IOException {

        ARXAnonymizer anonymizer = new ARXAnonymizer();
        Data.DefaultData data = Data.create();
        data.add("age", "gender", "zipcode");
        data.add("34", "male", "81667");
        data.add("45", "female", "81675");
        data.add("66", "male", "81925");
        data.add("70", "female", "81931");
        data.add("34", "female", "81931");
        data.add("70", "male", "81931");
        data.add("45", "male", "81931");

        HierarchyBuilderOrderBased<Long> builder = HierarchyBuilderOrderBased.create(DataType.INTEGER, false);

        // Define grouping fanouts
        builder.getLevel(0).addGroup(3, DataType.INTEGER.createAggregate().createIntervalFunction());

        data.getDefinition().setAttributeType("age", builder);

        // Create config
        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0d);
        ARXResult result = anonymizer.anonymize(data, config);

        DataHandle handle = result.getOutput();
        var coll = new ArrayList<String[]>();
        handle.iterator().forEachRemaining(coll::add);

        // Assert optimum result is Anonymious
        Assertions.assertEquals(ARXLattice.Anonymity.ANONYMOUS, result.getGlobalOptimum().getAnonymity());

        //Assert headers are in the first row of the result dataset
        Assertions.assertEquals("age", coll.get(0)[0]);
        Assertions.assertEquals("gender", coll.get(0)[1]);
        Assertions.assertEquals("zipcode", coll.get(0)[2]);

        //Assert value of the last row
        Assertions.assertEquals("[34, 45]", coll.get(7)[0]);
        Assertions.assertEquals("*", coll.get(7)[1]);
        Assertions.assertEquals("*", coll.get(7)[2]);
    }
}
