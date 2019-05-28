package no.oslomet.aaas.hierarchy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderBasedHierarchyBuilderTest {


    private String[] testData;

    @BeforeEach
    void setUp() {
        testData = new String[]{"Oslo", "Bergen", "Stockholm", "London", "Paris"};
    }

    @Test
    void build() {
        String[][] expected =
                {{"Oslo", "nordic-city", "*",},
                {"Bergen", "nordic-city", "*",},
                {"Stockholm", "nordic-city", "*",},
                {"London", "mid-european-city", "*",},
                {"Paris", "mid-european-city", "*",}};

        Level nordicGroup = new Level(0, List.of(new Level.Group(3, "nordic-city")));
        Level midEuroGroup = new Level(0, List.of(new Level.Group(2, "mid-european-city")));
        OrderBasedHierarchyBuilder basedHierarchyBuilder = new OrderBasedHierarchyBuilder(List.of(nordicGroup, midEuroGroup));
        String[][] result = basedHierarchyBuilder.build(testData).getHierarchy();

        Assertions.assertArrayEquals(expected, result);
    }
}