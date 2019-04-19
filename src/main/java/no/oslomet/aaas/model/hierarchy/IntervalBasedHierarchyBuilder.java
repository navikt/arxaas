package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

import java.util.List;

/**
 * Hierarchy Builder for interval hierarchies
 */
public class IntervalBasedHierarchyBuilder implements HierarchyBuilder {

    private final ARXDataType dataType;
    private final List<Interval> intervals;
    private final List<Level> levels;
    private Range lowerRange;
    private Range upperRange;

    @JsonCreator
    public IntervalBasedHierarchyBuilder(ARXDataType dataType,
                                         List<Interval> intervals,
                                         List<Level> levels,
                                         Range lowerRange,
                                         Range upperRange) {
        this.dataType = dataType;
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

        applyLevelsAndGroups(builder);

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

        for (Interval interval : intervals) {
            if(interval.getLabel() == null){

                builder.addInterval(interval.getFrom(), interval.getTo());
            }
            else {
                builder.addInterval(interval.getFrom(), interval.getTo(), interval.getLabel());
            }
        }
    }

    private void applyLevelsAndGroups(HierarchyBuilderIntervalBased builder){
        for (Level level: levels) {
            for (Level.Group group: level.getGroups()){
                builder.getLevel(level.getLevel()).addGroup(group.getGrouping());
            }
        }
    }

    public ARXDataType getDataType() {
        return dataType;
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

    public static class Interval {
        private final Long from;
        private final Long to;
        private final String label;

        @JsonCreator
        public Interval(Long from, Long to, String label) {
            this.from = from;
            this.to = to;
            this.label = label;
        }

        public Interval(Long from, Long to) {
            this.from = from;
            this.to = to;
            this.label = null;
        }


        public Long getFrom() {
            return from;
        }

        public Long getTo() {
            return to;
        }

        public String getLabel() {
            return label;
        }
    }


    public static class Range {
        private final Long snapFrom;
        private final Long bottomTopCodingFrom;
        private final Long minMaxValue;

        @JsonCreator
        public Range(Long snapFrom, Long bottomTopCodingFrom, Long minMaxValue) {
            this.snapFrom = snapFrom;
            this.bottomTopCodingFrom = bottomTopCodingFrom;
            this.minMaxValue = minMaxValue;
        }

        HierarchyBuilderIntervalBased.Range<Long> arxRange(){
            return new HierarchyBuilderIntervalBased.Range<>(
                    snapFrom,
                    bottomTopCodingFrom,
                    minMaxValue);
        }

        public Long getSnapFrom() {
            return snapFrom;
        }

        public Long getBottomTopCodingFrom() {
            return bottomTopCodingFrom;
        }

        public Long getMinMaxValue() {
            return minMaxValue;
        }
    }


}
