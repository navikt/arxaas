package no.oslomet.aaas.model.hierarchy.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.aggregates.HierarchyBuilderIntervalBased;

public class Interval {
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

    void applyTo(HierarchyBuilderIntervalBased<Long> builder) {
        if(getLabel() == null){
            builder.addInterval(getFrom(), getTo());
        }
        else {
            builder.addInterval(getFrom(), getTo(), getLabel());
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