package no.oslomet.aaas.model;

import no.oslomet.aaas.model.anonymity.PrivacyCriterionModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Request {

    @NotNull
    private final List<String[]> data;
    @NotNull
    private final List<Attribute> attributes;
    private final List<PrivacyCriterionModel> privacyModels;
    private final Double suppressionLimit;

    public Request(List<String[]> data, List<Attribute> attributes, List<PrivacyCriterionModel> privacyModels, Double suppressionLimit) {
        this.data = data;
        this.attributes = attributes;
        this.privacyModels = privacyModels;
        this.suppressionLimit = suppressionLimit;
    }


    public List<String[]> getData() {
        return data;
    }


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<PrivacyCriterionModel> getPrivacyModels() { return privacyModels;  }

    public Double getSuppressionLimit() { return suppressionLimit;    }

}
