package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ARXConfigurationFactory implements ConfigurationFactory {

    private ARXPrivacyCriterionFactory arxPrivacyCriterionFactory;

    @Autowired
    public ARXConfigurationFactory(ARXPrivacyCriterionFactory arxPrivacyCriterionFactory) {
        this.arxPrivacyCriterionFactory = arxPrivacyCriterionFactory;
    }

    @Override
    public ARXConfiguration create(MetaData metaData){
        ARXConfiguration config = ARXConfiguration.create();
        setSuppressionLimit(config);
        setPrivacyModels(config,metaData);
        return config;
    }

    /***
     * Mutates ARX {@link ARXConfiguration} object by setting the suppression limit configuration for anonymization.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymization/data set settings
     */
    private void setSuppressionLimit(ARXConfiguration config){
        config.setSuppressionLimit(0.02d);
    }

    /***
     * Mutates an ARX {@link ARXConfiguration} object by setting the privacy models defined by the payload.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymize/data set settings
     * @param metaData object containing parameters that defines the privacy models to be used
     */
    private void setPrivacyModels(ARXConfiguration config, MetaData metaData){
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : metaData.getModels().entrySet())
        {
            config.addPrivacyModel(getPrivacyModel(entry.getKey(),entry.getValue()));
        }
    }

    /**
     * Returns an Arx {@link PrivacyCriterion} object for the desired privacy object selected by the user.
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters that defines which settings to be used to created the privacy model
     * @return the {@link PrivacyCriterion} object created with the specified parameters
     */
    private PrivacyCriterion getPrivacyModel(PrivacyModel model, Map<String,String> params){
        return arxPrivacyCriterionFactory.create(model, params);
    }
}
