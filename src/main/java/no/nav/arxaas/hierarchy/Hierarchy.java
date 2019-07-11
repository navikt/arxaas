package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Hierarchy {

    private final String[][] hierarchy;

    @JsonCreator
    public Hierarchy(String[][] hierarchy){
        this.hierarchy = hierarchy;
    }

    public String[][] getHierarchy() {
        return hierarchy;
    }
}
