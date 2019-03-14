package no.oslomet.aaas.model;

import java.util.Map;

public class PrivacyModelModel {

    private final PrivacyModel privacyModel;
    private final Map<String, String> params;

    public PrivacyModelModel(PrivacyModel privacyModel, Map<String, String> params) {
        this.privacyModel = privacyModel;
        this.params = params;
    }

    public PrivacyModel getPrivacyModel() {
        return privacyModel;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
