package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.Valid;
import java.util.Map;

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
