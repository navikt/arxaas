package no.nav.arxaas.model;

import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FormMetaDataRequest {

    @NotNull
    private final List<FormDataAttribute> attributes;
    private final List<PrivacyCriterionModel> privacyModels;
    private final Double suppressionLimit;

    public FormMetaDataRequest(List<FormDataAttribute> attributes,
                               List<PrivacyCriterionModel> privacyModels,
                               Double suppressionLimit) {
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
