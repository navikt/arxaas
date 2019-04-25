package no.oslomet.aaas.model.hierarchy.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.oslomet.aaas.model.hierarchy.Hierarchy;
import no.oslomet.aaas.model.hierarchy.HierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.Level;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Hierarchy Builder for interval hierarchies.
 * Builds new hierarchies with the intervals and levels provided in the constructor
 */
public class IntervalBasedHierarchyBuilder implements HierarchyBuilder {

    @NotNull
    private final List<Interval> intervals;
    @NotNull
    @Valid
    private final List<Level> levels;
    private Range lowerRange;
    private Range upperRange;
    @NotNull
    private final BuilderDataType dataType;


    public enum BuilderDataType {
        LONG,
        DOUBLE
    }

    @JsonCreator
    public IntervalBasedHierarchyBuilder(
            List<Interval> intervals,
            List<Level> levels,
            Range lowerRange,
            Range upperRange, BuilderDataType dataType) {
        this.intervals = intervals;
        this.levels = levels;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
        this.dataType = dataType;
    }

    /**
     * Builds a Hierarchy for the provided column
     * @param column String[]
     * @return Hierarchy containing a String[][] hierarchy
     */
    @Override
    public Hierarchy build(String[] column) {
        if(dataType == BuilderDataType.LONG){
            HierarchyBuilderIntervalBased<Long> builder = arxHierarchyBuilderIntervalBased();
            applyIntervals(builder);
            applyLevels(builder);
            builder.prepare(column);
            return new Hierarchy(builder.build().getHierarchy());
        }
        else if (dataType == BuilderDataType.DOUBLE){
            HierarchyBuilderIntervalBased<Double> builder = arxHierarchyBuilderIntervalBasedDouble();
            applyIntervalsDouble(builder);
            applyLevels(builder);
            builder.prepare(column);
            return new Hierarchy(builder.build().getHierarchy());
        }
        throw new IllegalStateException("Datatype=" + dataType.toString() + " is not supported");
    }

    /**
     * Create HierarchyBuilderIntervalBased with right create method
     * @return HierarchyBuilderIntervalBased
     */
    private HierarchyBuilderIntervalBased<Long> arxHierarchyBuilderIntervalBased() {
        HierarchyBuilderIntervalBased<Long> builder;
        if(upperRange == null || lowerRange ==null){
            builder = HierarchyBuilderIntervalBased.create(DataType.INTEGER);
        }
        else {
            builder = HierarchyBuilderIntervalBased.create(
                    DataType.INTEGER,
                    lowerRange.arxRangeLong(),
                    upperRange.arxRangeLong());
        }
        builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, false));
        return builder;
    }

    private HierarchyBuilderIntervalBased<Double> arxHierarchyBuilderIntervalBasedDouble() {
        HierarchyBuilderIntervalBased<Double> builder;
        if(upperRange == null || lowerRange ==null){
            builder = HierarchyBuilderIntervalBased.create(DataType.DECIMAL);
        }
        else {
            builder = HierarchyBuilderIntervalBased.create(
                    DataType.DECIMAL,
                    lowerRange.arxRangeDouble(),
                    upperRange.arxRangeDouble());
        }
        builder.setAggregateFunction(DataType.DECIMAL.createAggregate().createIntervalFunction(true, false));
        return builder;
    }

    private void applyIntervals(HierarchyBuilderIntervalBased<Long> builder) {
        if(intervals != null){
            for (Interval interval : intervals) {
                interval.applyTo(builder);
            }
        }
    }

    private void applyIntervalsDouble(HierarchyBuilderIntervalBased<Double> builder) {
        if(intervals != null){
            for (Interval interval : intervals) {
                interval.applyToDouble(builder);
            }
        }
    }

    private void applyLevels(HierarchyBuilderIntervalBased builder){
        if (levels != null){
            for (Level level: levels) {
                level.applyTo(builder);
            }
        }
    }

    @Override
    public String toString() {
        return "IntervalBasedHierarchyBuilder{" +
                "intervals=" + intervals +
                ", levels=" + levels +
                ", lowerRange=" + lowerRange +
                ", upperRange=" + upperRange +
                '}';
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Range getLowerRange() {
        return lowerRange;
    }

    public Range getUpperRange() {
        return upperRange;
    }

    public BuilderDataType getDataType(){
        return dataType;
    }
}
