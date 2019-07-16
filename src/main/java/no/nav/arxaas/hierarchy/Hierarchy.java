package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.*;

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
        List<String> stringifiedHierarchy = new ArrayList<>();
        for (String[] strings : List.of(hierarchy)) {
            String s = Arrays.toString(strings);
            stringifiedHierarchy.add(s);
        }

        return "Hierarchy{" +
                "hierarchy=" + stringifiedHierarchy.toString() +
                '}';
    }
}
