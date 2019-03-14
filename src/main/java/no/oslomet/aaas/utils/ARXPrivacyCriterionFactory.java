package no.oslomet.aaas.utils;

import no.oslomet.aaas.exception.AaaSRuntimeException;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.criteria.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/***
 * Utility class for assigning a privacy model
 */
@Component
public class ARXPrivacyCriterionFactory {

    private static final String COLUMNNAME = "column_name";

    /**
     * Returns an Arx {@link PrivacyCriterion} object for the desired privacy object selected by the user.
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters that defines which settings to be used to created the privacy model
     * @return an ARX {@link PrivacyCriterion} object created with the specified parameters
     */
    PrivacyCriterion create(PrivacyModel model, Map<String,String> params){
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
