package no.oslomet.aaas.utils;

import no.oslomet.aaas.exception.AaaSRuntimeException;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.criteria.*;

import java.util.Map;

public class ARXConfigurationFactory {

    private final MetaData metaData;
    private static final String COLUMNNAME = "column_name";

    ARXConfigurationFactory(MetaData metaData){
        this.metaData = metaData;
    }

    public ARXConfiguration create(){
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
