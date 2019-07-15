package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Request {

    @NotNull
    private final List<String[]> data;
    @NotNull
    private final List<Attribute> attributes;
    private final List<PrivacyCriterionModel> privacyModels;
    private final Double suppressionLimit;

    @JsonCreator
    public Request(@JsonProperty("data") List<String[]> data,
                   @JsonProperty("attributes") List<Attribute> attributes,
                   @JsonProperty("privacyModels") List<PrivacyCriterionModel> privacyModels,
                   @JsonProperty("suppressionLimit") Double suppressionLimit) {
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
