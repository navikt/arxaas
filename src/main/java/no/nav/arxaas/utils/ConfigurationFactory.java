package no.nav.arxaas.utils;

import no.nav.arxaas.model.anonymity.PrivacyCriterionModel;
import org.deidentifier.arx.ARXConfiguration;

import java.util.List;

/**
 * Public contract to be fulfilled by data arxaas classes
 */
public interface ConfigurationFactory {

    /***
     * Returns an ARX {@link ARXConfiguration} object created from the provided payload
     * @param privacyModels Model object containing the parameters to use in arxaas
     * @param suppressionLimit a double containing the suppression limit value to be used in arxaas
     * @return ARX {@link ARXConfiguration} object containing the arxaas algorithm settings.
     */
    ARXConfiguration create(List<PrivacyCriterionModel> privacyModels, Double suppressionLimit);
}
