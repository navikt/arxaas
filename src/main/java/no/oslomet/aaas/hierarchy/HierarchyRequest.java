package no.oslomet.aaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;

/**
 * Understands a request to build a generalization hierarchy
 */

public class HierarchyRequest {

    private final String[] column;

    @Valid
    private final HierarchyBuilder builder;


    @JsonCreator
    public HierarchyRequest(String[] column, HierarchyBuilder builder) {
        this.column = column;
        this.builder = builder;
    }

    public String[] getColumn() {
        return column;
    }

    public HierarchyBuilder getBuilder() {
        return builder;
    }



}
