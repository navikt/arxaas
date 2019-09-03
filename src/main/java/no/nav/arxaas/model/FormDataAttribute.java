package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormDataAttribute {

    private final String field;
    private final AttributeTypeModel attributeTypeModel;
    private final Integer hierarchy;

    @JsonCreator
    public FormDataAttribute(@JsonProperty("field") String field,
                     @JsonProperty("attributeTypeModel") AttributeTypeModel attributeTypeModel,
                     @JsonProperty("hierarchy") Integer hierarchy) {
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

    public Integer getHierarchy() {
        return hierarchy;
    }
}
