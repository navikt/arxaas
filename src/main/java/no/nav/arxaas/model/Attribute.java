package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Attribute {

    private final String field;
    private final AttributeTypeModel attributeTypeModel;
    private final List<String[]> hierarchy;

    @JsonCreator
    public Attribute(@JsonProperty("field") String field,
                     @JsonProperty("attributeTypeModel") AttributeTypeModel attributeTypeModel,
                     @JsonProperty("hierarchy") List<String[]> hierarchy) {
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
