package no.oslomet.aaas.model.hierarchy.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.oslomet.aaas.model.hierarchy.Hierarchy;
import no.oslomet.aaas.model.hierarchy.HierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.Level;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

import java.util.List;

/**
 * Hierarchy Builder for interval hierarchies
 */
public class IntervalBasedHierarchyBuilder implements HierarchyBuilder {

    private final List<Interval> intervals;
    private final List<Level> levels;
    private Range lowerRange;
    private Range upperRange;

    @JsonCreator
    public IntervalBasedHierarchyBuilder(
                                         List<Interval> intervals,
                                         List<Level> levels,
                                         Range lowerRange,
                                         Range upperRange) {
        this.intervals = intervals;
        this.levels = levels;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    @Override
    public Hierarchy build(String[] column) {

        HierarchyBuilderIntervalBased<Long> builder = arxHierarchyBuilderIntervalBased();

        builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, false));
        applyIntervals(builder);
        applyLevels(builder);
        builder.prepare(column);
        return new Hierarchy(builder.build().getHierarchy());
    }

    /**
     * Create HierarchyBuilderIntervalBased with right create method
     * @return HierarchyBuilderIntervalBased
     */
    private HierarchyBuilderIntervalBased<Long> arxHierarchyBuilderIntervalBased() {
        if(upperRange == null || lowerRange ==null){
            return HierarchyBuilderIntervalBased.create(DataType.INTEGER);
        }
        return HierarchyBuilderIntervalBased.create(
                    DataType.INTEGER,
                    lowerRange.arxRange(),
                    upperRange.arxRange());
    }

    private void applyIntervals(HierarchyBuilderIntervalBased<Long> builder) {
        if(intervals != null){
            for (Interval interval : intervals) {
                interval.applyTo(builder);
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

}
