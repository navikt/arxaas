package no.nav.arxaas.model;

import java.util.List;

public class Attribute {

    private final String field;
    private final AttributeTypeModel attributeTypeModel;
    private final List<String[]> hierarchy;

    public Attribute(String field, AttributeTypeModel attributeTypeModel, List<String[]> hierarchy) {
        this.field = field;
        this.attributeTypeModel = attributeTypeModel;
        this.hierarchy = hierarchy;
    }

    public String getField() {
        return field;
    }

    public AttributeTypeModel getAttributeTypeModel() {
        return attributeTypeModel;
    }

    public List<String[]> getHierarchy() {
        return hierarchy;
    }
}
