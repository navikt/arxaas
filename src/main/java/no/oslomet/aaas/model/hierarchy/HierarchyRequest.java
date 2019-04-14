package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

/**
 * Understands a request to build a generalization hierarchy
 */

public class HierarchyRequest {

    private final String[] column;
    private final HierarchyBuilder builder;


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
