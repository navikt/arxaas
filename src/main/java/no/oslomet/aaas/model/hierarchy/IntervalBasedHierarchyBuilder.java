package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.juli.logging.Log;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

import java.util.List;


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

        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(
                DataType.INTEGER,
                lowerRange.arxRange(),
                upperRange.arxRange());

        // Define base intervals
        builder.setAggregateFunction(DataType.INTEGER.createAggregate().createIntervalFunction(true, false));
        applyIntervals(builder);

        applyLevelsAndGroups(builder);

        builder.prepare(column);
        return new Hierarchy(builder.build().getHierarchy());
    }

    private void applyIntervals(HierarchyBuilderIntervalBased<Long> builder) {

        var b = (HierarchyBuilderIntervalBased<Long>)builder;
        for (Interval interval : intervals) {
            if(interval.getLabel() == null){

                b.addInterval((Long)interval.getFrom(),(Long)interval.getTo());
            }
            else {
                b.addInterval((Long)interval.getFrom(), (Long)interval.getTo(), interval.getLabel());
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
            return new HierarchyBuilderIntervalBased.Range<Long>(
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
