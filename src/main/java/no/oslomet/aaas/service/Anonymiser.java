package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymizationResponsePayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AnonymizeResult;

public interface Anonymiser {

    AnonymizeResult anonymize(AnonymizationPayload payload);
}
