package no.oslomet.aaas.model;
import java.util.Map;

public class MetaData {

    private Map<String, SensitivityModel> sensitivityList;
    private Map<String, String> dataType;
    private Map<String, String[][]> hierarchy;
    private Map<PrivacyModel, Map<String, String>> models;

    public Map<String, SensitivityModel> getSensitivityList() {
        return sensitivityList;
    }

    public void setSensitivityList(Map<String, SensitivityModel> sensitivityList) {
        this.sensitivityList = sensitivityList;
    }

    public Map<String, String> getDataType() {
        return dataType;
    }

    public void setDataType(Map<String, String> dataType) {
        this.dataType = dataType;
    }

    public Map<String, String[][]> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Map<String, String[][]> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Map<PrivacyModel, Map<String, String>> getModels() {
        return models;
    }

    public void setModels(Map<PrivacyModel, Map<String, String>> models) {
        this.models = models;
    }


}
