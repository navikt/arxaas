package no.oslomet.aaas.model.hierarchy.interval;

import no.oslomet.aaas.model.hierarchy.Hierarchy;
import no.oslomet.aaas.model.hierarchy.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class IntervalBasedHierarchyBuilderTest {

    private List<Interval> testIntervals;
    private String[] testColumn;
    private List<Level> testLevels;

    @BeforeEach
    void setUp() {
        testIntervals = List.of(new Interval(0L,2L), new Interval(2L, 4L), new Interval(4L, 8L));
        testLevels = List.of(new Level(0, List.of(new Level.Group(2))));
        testColumn = getExampleData();
    }

    private static String[] getExampleData(){

        String[] result = new String[10];
        for (int i=0; i< result.length; i++){
            result[i] = String.valueOf(i);
        }
        return result;
    }


    @Test
    void build() {
        String[] expectedFirstRow = {"0", "[0, 2[", "[0, 4[", "*"};

        IntervalBasedHierarchyBuilder basedHierarchyBuilder = new IntervalBasedHierarchyBuilder(
                testIntervals,
                testLevels,
                new Range(0L, 0L, Long.MIN_VALUE / 4),
                new Range(81L, 100L, Long.MAX_VALUE / 4));

        Hierarchy result = basedHierarchyBuilder.build(testColumn);
        Assertions.assertArrayEquals(expectedFirstRow,result.getHierarchy()[0]);

    }


    @Test
    void build_with_labeled_intervals() {

        String[][] expected = {
                {"0", "young", "[0, 4[", "*"},
                {"1", "young", "[0, 4[", "*"},
                {"2", "adult", "[0, 4[", "*"},
                {"3", "adult", "[0, 4[", "*"},
                {"4", "old", "[4, 8[", "*"},
                {"5", "old", "[4, 8[", "*"},
                {"6", "old", "[4, 8[", "*"},
                {"7", "old", "[4, 8[", "*"},
                {"8", "very-old", "[8, 12[", "*"},
                {"9", "very-old", "[8, 12[", "*"}};



        List<Interval> labeledIntervals = List.of(
                new Interval(0L,2L, "young"),
                new Interval(2L, 4L, "adult"),
                new Interval(4L, 8L, "old"),
                new Interval(8L, Long.MAX_VALUE, "very-old"));

        IntervalBasedHierarchyBuilder basedHierarchyBuilder = new IntervalBasedHierarchyBuilder(
                labeledIntervals,
                testLevels,
                new Range(0L, 0L, Long.MIN_VALUE / 4),
                new Range(81L, 100L, Long.MAX_VALUE / 4));

        Hierarchy result = basedHierarchyBuilder.build(testColumn);
        Assertions.assertArrayEquals(expected, result.getHierarchy());
    }

}
