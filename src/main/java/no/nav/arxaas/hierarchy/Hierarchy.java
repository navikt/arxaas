package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy {

    private final String[][] hierarchy;

    @JsonCreator
    public Hierarchy(String[][] hierarchy){
        this.hierarchy = hierarchy;
    }

    public String[][] getHierarchy() {
        return hierarchy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hierarchy other = (Hierarchy) o;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(hierarchy);
    }


    @Override
    public String toString() {
        var stringifiedHierarchy = Arrays.stream(hierarchy)
                .map(Arrays::toString)
                .collect(Collectors.toList());

        return "Hierarchy{" +
                "hierarchy=" + stringifiedHierarchy.toString() +
                '}';
    }
}
