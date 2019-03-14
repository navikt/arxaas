package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.Attribute;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.PrivacyModelModel;
import org.deidentifier.arx.ARXConfiguration;

import java.util.List;

public interface ConfigurationFactory {
    ARXConfiguration create(MetaData metaData);

    ARXConfiguration create(List<PrivacyModelModel> privacyModels);
}
