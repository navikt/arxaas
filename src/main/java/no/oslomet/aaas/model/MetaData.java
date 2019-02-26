package no.oslomet.aaas.model;
import java.util.Map;

/***
 * Model class for the Metadata containing the anonymization and analysation settings.
 */
public class MetaData {

    private Map<String, AttributeTypeModel> attributeTypeList;
    private Map<String, String> dataType;
    private Map<String, String[][]> hierarchy;
    private Map<PrivacyModel, Map<String, String>> models;

    /***
     * Getter method that returns an object of {@link AttributeTypeModel}.
     * @return HashMap containing the dataset fields/columns and its attribute type object
     * from {@link AttributeTypeModel}
     */
    public Map<String, AttributeTypeModel> getAttributeTypeList() {
        return attributeTypeList;
    }

    /***
     * Setter method for the dataset fields/column attribute types.
     * @param AttributeTypeList HashMap containing a String of dataset fields/column and a
     *                          object of {@link AttributeTypeModel} containing the attribute types
     */
    public void setAttributeTypeList(Map<String, AttributeTypeModel> AttributeTypeList) {
        this.attributeTypeList = AttributeTypeList;
    }

    /***
     * Getter method for the dataset data type.
     * @return HashMap containing a String describing a dataset field/column and a String describing the
     * data type of that field/column
     */
    public Map<String, String> getDataType() {
        return dataType;
    }

    /***
     * Setter method for that dataset data type.
     * @param dataType HashMap containing a String describing a dataset field/column and a String describing
     *                 the data type of that field/column
     */
    public void setDataType(Map<String, String> dataType) {
        this.dataType = dataType;
    }

    /***
     * Getter method for the dataset hierarchy setting.
     * @return HashMap containing a String that describing a dataset field/column and a Sting array containing the
     * hierarchy setting for that dataset field/column
     */
    public Map<String, String[][]> getHierarchy() {
        return hierarchy;
    }

    /***
     * Setter method for the dataset hierarchy setting.
     * @param hierarchy HashMap containing a String that describing a dataset field/column and a Sting array containing
     *                  the hierarchy setting for that dataset field/column
     */
    public void setHierarchy(Map<String, String[][]> hierarchy) {
        this.hierarchy = hierarchy;
    }

    /***
     * Getter method for the privacy models used for the dataset
     * @return HashMap containing a object of {@link PrivacyModel} type used and a HashMap containing a String
     * describing the type of value and String describing its value
     */
    public Map<PrivacyModel, Map<String, String>> getModels() {
        return models;
    }

    /***
     * Setter method for the privacy models used for the dataset
     * @param models HashMap containing a object of {@link PrivacyModel} type used and a HashMap containing a String
     *               describing the type of value and String describing its value
     */
    public void setModels(Map<PrivacyModel, Map<String, String>> models) {
        this.models = models;
    }


}
