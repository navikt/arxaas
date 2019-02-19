package no.oslomet.aaas.analyse;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;

public interface Analyser {

    AnalysisResult analyse(AnalysationPayload payload);
}
