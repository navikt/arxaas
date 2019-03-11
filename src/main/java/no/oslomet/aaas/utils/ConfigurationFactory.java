package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.ARXConfiguration;

public interface ConfigurationFactory {
    ARXConfiguration create(MetaData metaData);
}
