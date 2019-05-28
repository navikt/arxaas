package no.oslomet.aaas.hierarchy.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

public class Interval {
    private final Number from;
    private final Number to;
    private final String label;

    @JsonCreator
    public Interval(Number from, Number to, String label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    Interval(Number from, Number to) {
        this.from = from;
        this.to = to;
        this.label = null;
    }


    public Number getFrom() {
        return from;
    }

    public Number getTo() {
        return to;
    }

    public String getLabel() {
        return label;
    }

    void applyTo(HierarchyBuilderIntervalBased<Long> builder) {
        if(getLabel() == null){
            builder.addInterval(getFrom().longValue(), getTo().longValue());
        }
        else {
            builder.addInterval(getFrom().longValue(), getTo().longValue(), getLabel());
        }
    }

    void applyToDouble(HierarchyBuilderIntervalBased<Double> builder) {
        if(getLabel() == null){
            builder.addInterval(getFrom().doubleValue(), getTo().doubleValue());
        }
        else {
            builder.addInterval(getFrom().doubleValue(), getTo().doubleValue(), getLabel());
        }
    }

    @Override
    public String toString() {
        return "Interval{" +
                "from=" + from +
                ", to=" + to +
                ", label='" + label + '\'' +
                '}';
    }
}