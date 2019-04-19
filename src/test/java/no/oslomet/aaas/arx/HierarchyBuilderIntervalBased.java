package no.oslomet.aaas.arx;

import org.deidentifier.arx.DataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HierarchyBuilderIntervalBased {

    @Test
    void ageIntervalBasedHierarchyBuilder(){

        String[][] expected = {{"18", "adult", "young", "somthing"},
                {"23", "adult", "young", "somthing"},
                {"2", "child", "young", "somthing"},
                {"24", "adult", "young", "somthing"},
                {"434", "most likely dead", "not-young", "somthing"},
                {"23", "adult", "young", "somthing"},
                {"2323", "most likely dead", "not-young", "somthing"},
                {"45", "adult", "young", "somthing"},
                {"2323", "most likely dead", "not-young", "somthing"},
                {"2", "child", "young", "somthing"},
                {"6", "child", "young", "somthing"},
                {"445", "most likely dead", "not-young", "somthing"},
                {"7", "child", "young", "somthing"},
                {"67", "old", "not-young", "somthing"},
                {"43", "adult", "young", "somthing"},
                {"43", "adult", "young", "somthing"},
                {"23", "adult", "young", "somthing"},
                {"56", "old", "not-young", "somthing"},
                {"87", "old", "not-young", "somthing"},
                {"34", "adult", "young", "somthing"},
                {"23", "adult", "young", "somthing"},
                {"12", "child", "young", "somthing"}};


        List<Integer> tmp = List.of(18, 23, 2,24 ,434, 23,2323,45,2323,2,6,445,7,67,43,43,23,56,87,34,23,12);

        // Create the builder
        org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased<Long> builder = org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased.create(DataType.INTEGER);

        builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, true));
        // Define base intervals
        builder.addInterval(0L, 18L, "child");
        builder.addInterval(18L, 51L, "adult");
        builder.addInterval(51L, 100L, "old");
        builder.addInterval(100L, 10000L, "most likely dead");




        // Define grouping fanouts

        builder.getLevel(0)
                .addGroup(2, "young")
                .addGroup(2, "not-young");
        builder.getLevel(1).addGroup(2, "somthing");


        builder.prepare(tmp.stream().map(Object::toString).toArray(String[]::new));


        //Build Hierarchy
        String[][] result = builder.build().getHierarchy();
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void ageIntervalBasedHierarchyBuilder__createIntervalFunction(){

        String[][] expected = {{"18", "[18, 51]", "young", "somthing"},
                {"23", "[18, 51]", "young", "somthing"},
                {"2", "[0, 18]", "young", "somthing"},
                {"24", "[18, 51]", "young", "somthing"},
                {"434", "[100, 10000]", "not-young", "somthing"},
                {"23", "[18, 51]", "young", "somthing"},
                {"2323", "[100, 10000]", "not-young", "somthing"},
                {"45", "[18, 51]", "young", "somthing"},
                {"2323", "[100, 10000]", "not-young", "somthing"},
                {"2", "[0, 18]", "young", "somthing"},
                {"6", "[0, 18]", "young", "somthing"},
                {"445", "[100, 10000]", "not-young", "somthing"},
                {"7", "[0, 18]", "young", "somthing"},
                {"67", "[51, 100]", "not-young", "somthing"},
                {"43", "[18, 51]", "young", "somthing"},
                {"43", "[18, 51]", "young", "somthing"},
                {"23", "[18, 51]", "young", "somthing"},
                {"56", "[51, 100]", "not-young", "somthing"},
                {"87", "[51, 100]", "not-young", "somthing"},
                {"34", "[18, 51]", "young", "somthing"},
                {"23", "[18, 51]", "young", "somthing"},
                {"12", "[0, 18]", "young", "somthing"}};



        List<Integer> tmp = List.of(18, 23, 2,24 ,434, 23,2323,45,2323,2,6,445,7,67,43,43,23,56,87,34,23,12);

        // Create the builder
        org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased<Long> builder = org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased.create(DataType.INTEGER);

        builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, true));
        // Define base intervals
        builder.addInterval(0L, 18L);
        builder.addInterval(18L, 51L);
        builder.addInterval(51L, 100L);
        builder.addInterval(100L, 10000L);




        // Define grouping fanouts

        builder.getLevel(0)
                .addGroup(2, "young")
                .addGroup(2, "not-young");
        builder.getLevel(1).addGroup(2, "somthing");


        builder.prepare(tmp.stream().map(Object::toString).toArray(String[]::new));


        //Build Hierarchy
        String[][] result = builder.build().getHierarchy();
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
        Assertions.assertArrayEquals(expected, result);
    }
}
