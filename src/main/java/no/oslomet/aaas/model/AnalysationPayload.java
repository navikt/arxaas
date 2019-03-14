package no.oslomet.aaas.model;

import java.util.List;
import java.util.Map;


/***
 * Model class for Analysation data payload containing the data to be analysed and the attribute types of the dataset
 * fields/columns.
 */
public class AnalysationPayload {

    private final List<String[]> data;
    private final Map<String, AttributeTypeModel> attributeTypes;

    /***
     * Setter method for Analysation payload. Fills the object with the necessary data to analyse against
     * re-identification risk.
     * @param data data to analyse against re-identification risk
     * @param attributeTypes HashMap containing a String of dataset fields/column and a object
     *                       of {@link AttributeTypeModel} containing the attribute types
     */
    public AnalysationPayload(List<String[]> data, Map<String, AttributeTypeModel> attributeTypes) {
        this.data = data;
        this.attributeTypes = attributeTypes;
    }


    /***
     * Getter method for the dataset in the Analysation model.
     * @return List of string array containing the tabular dataset to analyse against re-identification risk
     */
    public List<String[]> getData() {
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
