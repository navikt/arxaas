package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.Data;

import java.util.List;

public interface DataFactory {
    Data create(List<String[]> rawData, MetaData metaData);
}
