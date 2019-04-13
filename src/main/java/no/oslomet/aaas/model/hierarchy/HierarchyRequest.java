package no.oslomet.aaas.model.hierarchy;

import java.util.Map;

/**
 * Understands a request to build a generalization hierarchy
 */
public class HierarchyRequest {

    private final String[] column;
    private final HierarchyBuilder builder;
    private final Map<String,String> params;


    public enum HierarchyBuilder {
        REDUCTIONBASED
    }

    public HierarchyRequest(String[] column, HierarchyBuilder builder, Map<String, String> params) {
        this.column = column;
        this.builder = builder;
        this.params = params;
    }

    public String[] getColumn() {
        return column;
    }

    public HierarchyBuilder getBuilder() {
        return builder;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
