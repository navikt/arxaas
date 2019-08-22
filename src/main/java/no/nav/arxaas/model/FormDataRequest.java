package no.nav.arxaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FormDataRequest {

    @NotNull
    private final List<Attribute> attributes;
    private final List<PrivacyCriterionModel> privacyModels;
    private final Double suppressionLimit;

    @JsonCreator
    public FormDataRequest(@JsonProperty("attributes") List<Attribute> attributes,
                           @JsonProperty("privacyModels") List<PrivacyCriterionModel> privacyModels,
                           @JsonProperty("suppressionLimit") Double suppressionLimit) {
        this.attributes = attributes;
        this.privacyModels = privacyModels;
        this.suppressionLimit = suppressionLimit;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<PrivacyCriterionModel> getPrivacyModels() { return privacyModels;  }

    public Double getSuppressionLimit() { return suppressionLimit;    }

}
