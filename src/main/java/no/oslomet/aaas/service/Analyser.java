package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;

public interface Analyser {

    abstract AnalysisResult analyse(AnalysationPayload payload);
}
