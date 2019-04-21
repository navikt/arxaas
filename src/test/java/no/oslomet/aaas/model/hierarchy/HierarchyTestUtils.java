package no.oslomet.aaas.model.hierarchy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HierarchyTestUtils {

    public static void printHierarchy(String[][] result) {
        List.of(result).forEach(strings -> System.out.println(Arrays.toString(strings)));
    }

    public static void printReusableHierarchy(String[][] result){
        var stringList = List.of(result).stream()
                .map(HierarchyTestUtils::hierarchyRowString)
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
}
