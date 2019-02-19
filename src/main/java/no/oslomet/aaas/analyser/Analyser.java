package no.oslomet.aaas.analyser;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;

public interface Analyser {

    AnalysisResult analyse(AnalysationPayload payload);
}
