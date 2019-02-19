package no.oslomet.aaas.model;

import java.util.Map;


/***
 * Model class for Analysation data payload
 */
public class AnalysationPayload {

    private final String data;
    private final Map<String, SensitivityModel> attributeTypes;

    /***
     *
     * @param data data to analyse re-identification risk
     * @param attributeTypes AttributeTypes for the data fields/columns
     */
    public AnalysationPayload(String data, Map<String, SensitivityModel> attributeTypes) {
        this.data = data;
        this.attributeTypes = attributeTypes;
    }

    public String getData() {
        return data;
    }

    public Map<String, SensitivityModel> getAttributeTypes() {
        return attributeTypes;
    }
}
