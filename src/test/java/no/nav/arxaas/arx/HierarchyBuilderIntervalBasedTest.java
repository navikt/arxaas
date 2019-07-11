package no.nav.arxaas.arx;

import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class HierarchyBuilderIntervalBasedTest {

    private static void printHierarchy(String[][] result) {
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
    }

    private static void printReusableHierarchy(String[][] result){
        var stringList = List.of(result).stream()
                .map(HierarchyBuilderIntervalBasedTest::hierarchyRowString)
                .collect(Collectors.toList());
        System.out.print("{");
        stringList.forEach(System.out::println);
        System.out.print("}");
    }

    private static String hierarchyRowString(String[] strings){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        List.of(strings).forEach(s -> builder.append("\"").append(s).append("\", "));
        builder.deleteCharAt(builder.length() -1);
        builder.append("},");
        return builder.toString();
    }

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
        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(DataType.INTEGER);

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
        //printHierarchy(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void ageIntervalBasedHierarchyBuilder__createIntervalFunction(){

        String[][] expected =
                {{"18", "[18, 51]", "young", "somthing"},
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
                {"23", "[18, 51]", "young", "somthing"},
                {"12", "[0, 18]", "young", "somthing"}};



        List<Integer> tmp = List.of(18, 23, 2,24 ,434, 23,2323,45,2323,2,6,445,7,67,43,43,23,56,87,23,12);

        // Create the builder
        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(DataType.INTEGER);

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
        //printHierarchy(result);
        Assertions.assertArrayEquals(expected, result);
    }


    @Test
    void arxExample(){

        String[][] expected = {{"18", "[0, 20]", "[0, 33]", "[0, 99]", "*",},
                {"20", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"23", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"2", "[0, 20]", "[0, 33]", "[0, 99]", "*",},
                {"24", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"434", ">=100", ">=100", ">=100", "*",},
                {"23", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"2323", ">=100", ">=100", ">=100", "*",},
                {"45", "[33, 53]", "[33, 66]", "[0, 99]", "*",},
                {"2323", ">=100", ">=100", ">=100", "*",},
                {"2", "[0, 20]", "[0, 33]", "[0, 99]", "*",},
                {"6", "[0, 20]", "[0, 33]", "[0, 99]", "*",},
                {"445", ">=100", ">=100", ">=100", "*",},
                {"7", "[0, 20]", "[0, 33]", "[0, 99]", "*",},
                {"67", "[66, 86]", "[66, 99]", "[0, 99]", "*",},
                {"43", "[33, 53]", "[33, 66]", "[0, 99]", "*",},
                {"43", "[33, 53]", "[33, 66]", "[0, 99]", "*",},
                {"23", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"56", "[53, 66]", "[33, 66]", "[0, 99]", "*",},
                {"87", "[86, 99]", "[66, 99]", "[0, 99]", "*",},
                {"34", "[33, 53]", "[33, 66]", "[0, 99]", "*",},
                {"23", "[20, 33]", "[0, 33]", "[0, 99]", "*",},
                {"12", "[0, 20]", "[0, 33]", "[0, 99]", "*",}};

        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(
                DataType.INTEGER,
                new HierarchyBuilderIntervalBased.Range<>(0L,0L,Long.MIN_VALUE / 4),
                new HierarchyBuilderIntervalBased.Range<>(100L,100L,Long.MAX_VALUE / 4));

        // Define base intervals
        //builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, true));
        builder.addInterval(0L, 20L);
        builder.addInterval(20L, 33L);

        // Define grouping fanouts
        builder.getLevel(0).addGroup(2);
        builder.getLevel(1).addGroup(3);

        List<Integer> tmp = List.of(18,20, 23, 2,24 ,434, 23,2323,45,2323,2,6,445,7,67,43,43,23,56,87,34,23,12);
        builder.prepare(tmp.stream().map(Object::toString).toArray(String[]::new));
        System.out.println("Resulting levels: "+Arrays.toString(builder.prepare(tmp.stream().map(Object::toString).toArray(String[]::new))));
        var hierarchy = builder.build().getHierarchy();
        //printReusableHierarchy(hierarchy);
        Assertions.assertArrayEquals(expected, hierarchy);
    }
}
