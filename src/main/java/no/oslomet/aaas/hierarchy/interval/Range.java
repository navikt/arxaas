package no.oslomet.aaas.hierarchy.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

public class Range {
    private final Long snapFrom;
    private final Long bottomTopCodingFrom;
    private final Long minMaxValue;

    @JsonCreator
    public Range(Long snapFrom, Long bottomTopCodingFrom, Long minMaxValue) {
        this.snapFrom = snapFrom;
        this.bottomTopCodingFrom = bottomTopCodingFrom;
        this.minMaxValue = minMaxValue;
    }

    HierarchyBuilderIntervalBased.Range<Long> arxRangeLong(){
        return new HierarchyBuilderIntervalBased.Range<>(
                snapFrom,
                bottomTopCodingFrom,
                minMaxValue);
    }

    HierarchyBuilderIntervalBased.Range<Double> arxRangeDouble(){
        return new HierarchyBuilderIntervalBased.Range<>(
                snapFrom.doubleValue(),
                bottomTopCodingFrom.doubleValue(),
                minMaxValue.doubleValue());
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