package no.nav.arxaas.model;

import org.deidentifier.arx.AttributeType;

/***
 * Model class for Attribute types. Sets the Attribute types of the dataset for arxaas and analysation.
 */
public enum AttributeTypeModel {

    SENSITIVE(AttributeType.SENSITIVE_ATTRIBUTE),
    INSENSITIVE(AttributeType.INSENSITIVE_ATTRIBUTE),
    IDENTIFYING(AttributeType.IDENTIFYING_ATTRIBUTE),
    QUASIIDENTIFYING(AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);

    private String name;
    private AttributeType attributeType;

    /***
     * Setter method for the attribute type
     * @param attributeType ARX object of {@link AttributeType} that sets attribute type
     */
    AttributeTypeModel(AttributeType attributeType){
        this.name = attributeType.toString();
        this.attributeType = attributeType;
    }

    /***
     * Getter method for the attribute type name
     * @return String containing the name of the attribute type
     */
    public String getName() {
        return name;
    }

    /***
     * Getter method for the ARX object of {@link AttributeType}
     * @return ARX object of {@link AttributeType} containing the type of attribute.
     */
    public AttributeType getAttributeType() { return attributeType; }
}
