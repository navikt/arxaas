package no.oslomet.aaas.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Request {

    @NotNull
    private final List<String[]> data;
    @NotNull
    private final List<Attribute> attributes;
    private final List<PrivacyModelModel> privacyModels;

    public Request(List<String[]> data, List<Attribute> attributes, List<PrivacyModelModel> privacyModels) {
        this.data = data;
        this.attributes = attributes;
        this.privacyModels = privacyModels;
    }


    public List<String[]> getData() {
        return data;
    }


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<PrivacyModelModel> getPrivacyModels() {
        return privacyModels;
    }

}
