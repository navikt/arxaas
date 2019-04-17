package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.PrivacyCriterionModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.criteria.PrivacyCriterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ARXConfigurationFactory implements ConfigurationFactory {

    private ARXPrivacyCriterionFactory arxPrivacyCriterionFactory;

    @Autowired
    public ARXConfigurationFactory(ARXPrivacyCriterionFactory arxPrivacyCriterionFactory) {
        this.arxPrivacyCriterionFactory = arxPrivacyCriterionFactory;
    }

    @Override
    public ARXConfiguration create(List<PrivacyCriterionModel> privacyModels , double suppressionLimit){
        ARXConfiguration config = ARXConfiguration.create();
        setSuppressionLimit(config,suppressionLimit);
        setPrivacyModels(config, privacyModels);
        return config;
    }

    /***
     * Mutates ARX {@link ARXConfiguration} object by setting the suppression limit configuration for anonymization.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymization/data set settings
     * @param suppressionLimit a double containing the value to be set as the suppression limit
     */
    private void setSuppressionLimit(ARXConfiguration config, double suppressionLimit){
            config.setSuppressionLimit(suppressionLimit);
    }

    /***
     * Mutates an ARX {@link ARXConfiguration} object by setting the privacy models defined by the payload.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymize/data set settings
     * @param privacyModels a List of {@link PrivacyCriterionModel} object containing parameters that defines the privacy models to be used
     */
    private void setPrivacyModels(ARXConfiguration config, List<PrivacyCriterionModel> privacyModels){
        for (PrivacyCriterionModel model: privacyModels) {
            config.addPrivacyModel(getPrivacyModel(model.getPrivacyModel(), model.getParams()));
        }
    }

    /**
     * Returns an Arx {@link PrivacyCriterionModel} object for the desired privacy object selected by the user.
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters that defines which settings to be used to created the privacy model
     * @return the {@link PrivacyCriterionModel} object created with the specified parameters
     */
    private PrivacyCriterion getPrivacyModel(PrivacyCriterionModel.PrivacyModel model, Map<String,String> params){
        return arxPrivacyCriterionFactory.create(model, params);
    }
}
