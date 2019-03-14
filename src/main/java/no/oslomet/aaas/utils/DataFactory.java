package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.Request;
import org.deidentifier.arx.Data;

public interface DataFactory {
    Data create(AnonymizationPayload payload);

    Data create(AnalysationPayload payload);

    Data create(Request payload);
}
