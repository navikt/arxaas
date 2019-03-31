package no.oslomet.aaas.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReIdentificationRisk {

    private final Map<String, String> measures;

    public ReIdentificationRisk(Map<String, String> measures) {
        this.measures  = new HashMap<>(measures);
    }

    public Map<String, String> getMeasures() {
        return measures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReIdentificationRisk that = (ReIdentificationRisk) o;
        return measures.equals(that.measures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measures);
    }
}
