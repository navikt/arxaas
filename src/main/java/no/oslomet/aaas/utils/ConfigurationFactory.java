package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModelModel;
import org.deidentifier.arx.ARXConfiguration;

import java.util.List;

/**
 * Public contract to be fulfilled by data anonymization classes
 */
public interface ConfigurationFactory {

    /***
     * Returns an ARX {@link ARXConfiguration} object created from the provided payload
     * @param metaData odel object containing the parameters to use in anonymization
     * @return ARX {@link ARXConfiguration} object containing the anonymization algorithm settings.
     */
    ARXConfiguration create(MetaData metaData);

    ARXConfiguration create(List<PrivacyModelModel> privacyModels);
}
