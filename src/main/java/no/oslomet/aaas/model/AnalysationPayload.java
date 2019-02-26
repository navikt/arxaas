package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for Analysation data payload
 */
public class AnalysationPayload {

    private final String data;
    private final Map<String, SensitivityModel> attributeTypes;

    /***
     * Setter method for Analysation payload. Fills the object with the necessary data to analyse against
     * re-identification risk.
     * @param data data to analyse against re-identification risk
     * @param attributeTypes AttributeTypes for the data fields/columns
     */
    public AnalysationPayload(String data, Map<String, SensitivityModel> attributeTypes) {
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
     * @return Map containing the dataset fields/column and which attribute type they have
     */
    public Map<String, SensitivityModel> getAttributeTypes() {
        return attributeTypes;
    }
}
