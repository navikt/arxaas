package no.oslomet.aaas.model;

import org.deidentifier.arx.AttributeType;

/***
 * Model class for Anonymity. Sets the anonymous status of the dataset after anonymization.
 */
public enum AttributeTypeModel {

    SENSITIVE(AttributeType.SENSITIVE_ATTRIBUTE),
    INSENSITIVE(AttributeType.INSENSITIVE_ATTRIBUTE),
    IDENTIFYING(AttributeType.IDENTIFYING_ATTRIBUTE),
    QUASIIDENTIFYING(AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);

    private String name;
    private AttributeType attributeType;

    AttributeTypeModel(AttributeType attributeType){
        this.name = attributeType.toString();
        this.attributeType = attributeType;
    }

    public String getName() {
        return name;
    }
    public AttributeType getAttributeType() { return attributeType; }
}
