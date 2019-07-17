package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderDate;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DateBasedHierarchyBuilder implements HierarchyBuilder {

    /**
     * Granularity from ARX
     * Copied from ARX to retain modularity
     */
    public enum Granularity {

        /**  Granularity */
        SECOND_MINUTE_HOUR_DAY_MONTH_YEAR(HierarchyBuilderDate.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR),
        /**  Granularity */
        MINUTE_HOUR_DAY_MONTH_YEAR(HierarchyBuilderDate.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR),
        /**  Granularity */
        HOUR_DAY_MONTH_YEAR(HierarchyBuilderDate.Granularity.HOUR_DAY_MONTH_YEAR),
        /**  Granularity */
        DAY_MONTH_YEAR(HierarchyBuilderDate.Granularity.DAY_MONTH_YEAR),
        /**  Granularity */
        WEEK_MONTH_YEAR(HierarchyBuilderDate.Granularity.WEEK_MONTH_YEAR),
        /**  Granularity */
        WEEK_YEAR(HierarchyBuilderDate.Granularity.WEEK_YEAR),
        /**  Granularity */
        MONTH_YEAR(HierarchyBuilderDate.Granularity.MONTH_YEAR),
        /**  Granularity */
        WEEKDAY(HierarchyBuilderDate.Granularity.WEEKDAY),
        /**  Granularity */
        WEEK(HierarchyBuilderDate.Granularity.WEEK),
        /**  Granularity */
        QUARTER(HierarchyBuilderDate.Granularity.QUARTER),
        /**  Granularity */
        YEAR(HierarchyBuilderDate.Granularity.YEAR),
        /**  Granularity */
        DECADE(HierarchyBuilderDate.Granularity.DECADE),
        /**  Granularity */
        CENTURY(HierarchyBuilderDate.Granularity.CENTURY),
        /**  Granularity */
        MILLENIUM(HierarchyBuilderDate.Granularity.MILLENIUM);

        /** Format string */
        private HierarchyBuilderDate.Granularity arxGranularity;

        /**
         * Creates a new instance
         * @param arxGranularity ARX version of the Enum
         */
        Granularity(HierarchyBuilderDate.Granularity arxGranularity) {
            this.arxGranularity = arxGranularity;
        }

        private HierarchyBuilderDate.Granularity arxGranularity(){
            return arxGranularity;
        }
    }
    @NotNull
    private final String dateFormat;
    @NotNull
    private final List<Granularity> granularities;

    @JsonCreator
    public DateBasedHierarchyBuilder(String dateFormat, List<Granularity> granularities) {
        Objects.requireNonNull(dateFormat, "dateFormat should not be null");
        Objects.requireNonNull(granularities, "granularities should not be null");
        if(granularities.isEmpty()) throw new IllegalArgumentException("granularities cannot be empty");
        this.dateFormat = dateFormat;
        this.granularities = granularities;
    }

    @Override
    public Hierarchy build(String[] column) {
        DataType<Date> dateType = DataType.createDate(dateFormat);
        var builder = HierarchyBuilderDate.create(dateType, arxGranularities());
        builder.prepare(column);
        return new Hierarchy(builder.build().getHierarchy());
    }

    private HierarchyBuilderDate.Granularity[] arxGranularities(){
        return granularities
                .stream()
                .map(Granularity::arxGranularity)
                .toArray(HierarchyBuilderDate.Granularity[]::new);
    }

    @JsonGetter("dateFormat")
    String getDateFormat() {
        return dateFormat;
    }

    @JsonGetter("granularities")
    List<Granularity> getGranularities() {
        return granularities;
    }
}
