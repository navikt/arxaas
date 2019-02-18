package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymisationResponsePayload;
import no.oslomet.aaas.model.AnonymizationPayload;

public interface Anonymiser {

    abstract AnonymisationResponsePayload anonymize(AnonymizationPayload payload);
}
