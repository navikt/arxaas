package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for Analysation data payload.
 */
public class AnalysationPayload {

    private final String data;
    private final Map<String, AttributeTypeModel> attributeTypes;

    /***
     * Setter method for Analysation payload. Fills the object with the necessary data to analyse against
     * re-identification risk.
     * @param data data to analyse against re-identification risk
     * @param attributeTypes HashMap containing a String of dataset fields/column and a object
     *                       of {@link AttributeTypeModel} containing the attribute types
     */
    public AnalysationPayload(String data, Map<String, AttributeTypeModel> attributeTypes) {
        this.data = data;
        this.attributeTypes = attributeTypes;
    }

    /***
     * Getter method for the dataset in the Analysation model.
     * @return String containing the tabular dataset to analyse against re-identification risk
     */
    public String getData() {
        return data;
    }

    /***
     * Getter method for the AttributeTypes for the dataset fields/columns.
     * @return HashMap containing a String of dataset fields/column and object
     * of {@link AttributeTypeModel} containing the attribute types
     */
    public Map<String, AttributeTypeModel> getAttributeTypes() {
        return attributeTypes;
    }
}
