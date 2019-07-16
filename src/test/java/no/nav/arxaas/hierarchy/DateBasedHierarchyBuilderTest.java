package no.nav.arxaas.hierarchy;

import org.deidentifier.arx.aggregates.HierarchyBuilderDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DateBasedHierarchyBuilderTest {

    private String[] testData;

    @BeforeEach
    void setUp() {
        testData = new String[]{"2020-07-16 15:28:024", "2019-07-16 16:38:025", "2019-07-16 17:48:025", "2019-07-16 18:48:025", "2019-06-16 19:48:025", "2019-06-16 20:48:025"};
    }

    @Test
    void build() {
        String stringDateFormat = "yyyy-MM-dd HH:mm:SSS";

        String[][] expected = {{"2020-07-16 15:28:024", "16.07.2020-15:28:00", "16.07.2020-15:28", "16.07.2020-15:00", "16.07.2020"},
                {"2019-07-16 16:38:025", "16.07.2019-16:38:00", "16.07.2019-16:38", "16.07.2019-16:00", "16.07.2019"},
                {"2019-07-16 17:48:025", "16.07.2019-17:48:00", "16.07.2019-17:48", "16.07.2019-17:00", "16.07.2019"},
                {"2019-07-16 18:48:025", "16.07.2019-18:48:00", "16.07.2019-18:48", "16.07.2019-18:00", "16.07.2019"},
                {"2019-06-16 19:48:025", "16.06.2019-19:48:00", "16.06.2019-19:48", "16.06.2019-19:00", "16.06.2019"},
                {"2019-06-16 20:48:025", "16.06.2019-20:48:00", "16.06.2019-20:48", "16.06.2019-20:00", "16.06.2019"}};

        Hierarchy expectedHiearchy = new Hierarchy(expected);
        var granularities = List.of(
                DateBasedHierarchyBuilder.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.DAY_MONTH_YEAR);

        DateBasedHierarchyBuilder builder = new DateBasedHierarchyBuilder(stringDateFormat, granularities);
        Assertions.assertEquals(expectedHiearchy, builder.build(testData));
    }

    @Test
    void build__with_one_value_in_column() {
        String stringDateFormat = "yyyy-MM-dd HH:mm:SSS";
        var singleValueColumn = new String[]{"2020-07-16 15:28:024"};
        String[][] expected = {{"2020-07-16 15:28:024", "16.07.2020-15:28:00", "16.07.2020-15:28", "16.07.2020-15:00", "16.07.2020"}};

        Hierarchy expectedHiearchy = new Hierarchy(expected);
        var granularities = List.of(
                DateBasedHierarchyBuilder.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.DAY_MONTH_YEAR);

        DateBasedHierarchyBuilder builder = new DateBasedHierarchyBuilder(stringDateFormat, granularities);
        Assertions.assertEquals(expectedHiearchy, builder.build(singleValueColumn));
    }

    @Test
    void build__with_invalid_date_format() {
        String stringDateFormat = "sdgsdgdf";

        var granularities = List.of(
                DateBasedHierarchyBuilder.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.DAY_MONTH_YEAR);

        DateBasedHierarchyBuilder builder = new DateBasedHierarchyBuilder(stringDateFormat, granularities);
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> builder.build(testData));
    }

    @Test
    void build__with_invalid_value_in_column() {
        String stringDateFormat_1 = "yyyy-MM-dd HH:mm:SSS";
        String stringDateFormat_2 = "yyyy-MM-dd HH:mm";
        var columnWithInvalidValue = new String[]{"2020-07-16 15:28:024", "2019-07-16"};

        var granularities = List.of(
                DateBasedHierarchyBuilder.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.DAY_MONTH_YEAR);

        DateBasedHierarchyBuilder builder = new DateBasedHierarchyBuilder(stringDateFormat_1, granularities);
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> builder.build(columnWithInvalidValue));

        DateBasedHierarchyBuilder builder2 = new DateBasedHierarchyBuilder(stringDateFormat_2, granularities);
        Assertions.assertThrows(java.lang.IllegalArgumentException.class, () -> builder2.build(columnWithInvalidValue));
    }
}
