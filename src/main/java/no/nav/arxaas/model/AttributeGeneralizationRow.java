package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for formatting Attribute generalization data so they can be stored with collections
 */
public class AttributeGeneralizationRow {
    final String name;
    final String type;
    final int generalizationLevel;

    /**
     * Constructor setting initial data
     * @param name Name of the attribute
     * @param type Identifying type of attribute. Such as QUASI_IDENTIFYING, SENSITIVE, etc
     * @param generalizationLevel the generalization level used to anonymize the dataset.
     */
    @JsonCreator
    public AttributeGeneralizationRow(@JsonProperty("name") String name,
                                      @JsonProperty("type") String type,
                                      @JsonProperty("generalizationLevel") int generalizationLevel){
        this.name = name;
        this.type = type;
        this.generalizationLevel = generalizationLevel;
    }

    public String getName() {
        return name;
    }

    public int getGeneralizationLevel() {
        return generalizationLevel;
    }

    public String getType() {
        return type;
    }
}
