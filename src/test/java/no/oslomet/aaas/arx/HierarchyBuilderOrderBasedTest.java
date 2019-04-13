package no.oslomet.aaas.arx;

import no.oslomet.aaas.GenerateTestData;
import org.deidentifier.arx.*;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;
import org.deidentifier.arx.aggregates.HierarchyBuilderOrderBased;
import org.deidentifier.arx.criteria.KAnonymity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class HierarchyBuilderOrderBasedTest {

    private final String[] testData = getExampleData();

    private static Data.DefaultData getExamleDefaultData(){
        Data.DefaultData data = Data.create();
        data.add("id", "name");

        for (int i=1; i< 100; i++){
            data.add(String.valueOf(i), i + "Name");
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
    void generateHierarchy() {

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
    void anonymizeUsingGeneratedHierarchy() throws IOException {

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
    void anonymizeDefaultDataUsingGeneratedHierarchy() throws IOException {

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
        Assertions.assertEquals("[34, 66]", coll.get(7)[0]);
        Assertions.assertEquals("*", coll.get(7)[1]);
        Assertions.assertEquals("*", coll.get(7)[2]);
    }

    @Test
    void testStringHierarchyBuilding(){

        List<String> column = List.of("Oslo", "London", "Tokyo", "Tokyo", "Bejing", "Bergen", "Moscow", "Praha");


        var it = column.iterator();
        it.forEachRemaining(System.out::println);

        HierarchyBuilderOrderBased<String> builder
                = HierarchyBuilderOrderBased.create(DataType.ORDERED_STRING, false);

        builder.getLevel(0)
                .addGroup(2, "European").addGroup(2,"Asian").addGroup(2, "eastern-eropean");


        // Define grouping fanouts
//        builder.getLevel(0)
//                .addGroup(2, "WESTERN")
//                .addGroup(2, "ASIAN");

        builder.prepare(column.toArray(new String[8]));





        //Build Hierarchy
        String[][] result = builder.build().getHierarchy();
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
    }


    @Test
    void ageIntervalBasedHierarchyBuilder(){
        List<Integer> tmp = List.of(18, 23, 2,24 ,434, 23,2323,45,2323,2,6,445,7,67,43,43,23,56,87,34,23,12);


        // Create the builder
        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(DataType.INTEGER);

        // Define base intervals
        builder.addInterval(0L, 18L, "child");
        builder.addInterval(18L, 51L, "adult");
        builder.addInterval(51L, 100L, "old");
        builder.addInterval(100L, 10000L, "most likely dead");




        // Define grouping fanouts

        builder.getLevel(0)
                .addGroup(2, "young")
                .addGroup(2, "not-young");


        builder.prepare(tmp.stream().map(Object::toString).toArray(String[]::new));


        //Build Hierarchy
        String[][] result = builder.build().getHierarchy();
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
    }


}
