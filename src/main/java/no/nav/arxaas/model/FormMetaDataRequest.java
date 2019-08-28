package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FormMetaDataRequest {

    @NotNull
    private final List<FormDataAttribute> attributes;
    private final List<PrivacyCriterionModel> privacyModels;
    private final Double suppressionLimit;

    @JsonCreator
    public FormMetaDataRequest(@JsonProperty("attributes") List<FormDataAttribute> attributes,
                               @JsonProperty("privacyModels") List<PrivacyCriterionModel> privacyModels,
                               @JsonProperty("suppressionLimit") Double suppressionLimit) {
        this.attributes = attributes;
        this.privacyModels = privacyModels;
        this.suppressionLimit = suppressionLimit;
    }

    public List<FormDataAttribute> getAttributes() {
        return attributes;
    }

    public List<PrivacyCriterionModel> getPrivacyModels() { return privacyModels;  }

    public Double getSuppressionLimit() { return suppressionLimit;    }

}
