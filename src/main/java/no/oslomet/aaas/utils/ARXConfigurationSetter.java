package no.oslomet.aaas.utils;

import no.oslomet.aaas.exception.AaaSRuntimeException;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.criteria.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ARXConfigurationSetter {

    private static final String COLUMNNAME = "column_name";

    /***
     * Sets the suppression limit configuration for anonymization in the
     * ARX {@link ARXConfiguration} object, then returns it.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymization/data set settings
     * @return an ARX {@link ARXConfiguration} object with the suppression setting
     */
    public ARXConfiguration setSuppressionLimit(ARXConfiguration config){
        config.setSuppressionLimit(0.02d);
        return config;
    }

    /***
     * Returns an ARX {@link ARXConfiguration} object that sets the privacy models defined by the payload.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymize/data set settings
     * @param payload map containing parameters that defines the privacy models to be used
     * @return an ARX {@link ARXConfiguration} object with the assigned privacy models settings
     */
    public ARXConfiguration setPrivacyModels(ARXConfiguration config, AnonymizationPayload payload){
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : payload.getMetaData().getModels().entrySet())
        {
            config.addPrivacyModel(getPrivacyModel(entry.getKey(),entry.getValue()));
        }
        return config;
    }

    /**
     * Returns an Arx {@link PrivacyCriterion} object for the desired privacy object selected by the user.
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters that defines which settings to be used to created the privacy model
     * @return the {@link PrivacyCriterion} object created with the specified parameters
     */
    private PrivacyCriterion getPrivacyModel(PrivacyModel model, Map<String,String> params){
        switch(model){
            case KANONYMITY:
                return new KAnonymity(Integer.parseInt(params.get("k")));
            case LDIVERSITY_DISTINCT:
                return new DistinctLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")));
            case LDIVERSITY_SHANNONENTROPY:
                return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                        EntropyLDiversity.EntropyEstimator.SHANNON);
            case LDIVERSITY_GRASSBERGERENTROPY:
                return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                        EntropyLDiversity.EntropyEstimator.GRASSBERGER);
            case LDIVERSITY_RECURSIVE:
                return new RecursiveCLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                        Integer.parseInt(params.get("c")));
            default:
                throw new AaaSRuntimeException(model.getName() + " Privacy Model not supported");
        }
    }
}
