package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AnonymizeResult;

public interface Anonymiser {

    AnonymizeResult anonymize(AnonymizationPayload payload);
}
