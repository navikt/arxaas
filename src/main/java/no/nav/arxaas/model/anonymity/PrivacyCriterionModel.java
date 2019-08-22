package no.nav.arxaas.model.anonymity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PrivacyCriterionModel {

    private final PrivacyModel privacyModel;
    private final Map<String, String> params;

    @JsonCreator
    public PrivacyCriterionModel(@JsonProperty("privacyModel") PrivacyModel privacyModel,
                                 @JsonProperty("params") Map<String, String> params) {
        this.privacyModel = privacyModel;
        this.params = params;
    }

    public PrivacyModel getPrivacyModel() {
        return privacyModel;
    }

    public Map<String, String> getParams() {
        return params;
    }



    /***
     * Model enum for PrivacyCriterionModel. Designates the Privacy model to be used in the arxaas process.
     */
    public enum PrivacyModel {

        KANONYMITY("K-Anonymity"),
        LDIVERSITY_DISTINCT("L-Diversity-Distinct"),
        LDIVERSITY_GRASSBERGERENTROPY("L-Diversity-Grassberger-Entropy"),
        LDIVERSITY_SHANNONENTROPY("L-Diversity-Shannon-Entropy"),
        LDIVERSITY_RECURSIVE("L-Diversity-Recursive"),
        TCLOSENESS_EQUAL_DISTANCE("T-Closeness-Equal-Distance"),
        TCLOSENESS_ORDERED_DISTANCE("T-Closeness-Ordered-Distance");

        private String name;

        /***
         * Setter method for the privacy medel.
         * @param name String containing the type of privacy model to be used.
         */
        PrivacyModel(String name){
            this.name = name;
        }

        /***
         * Getter method for the privacy model used.
         * @return String containing the name of the privacy model used.
         */
        public String getName() {
            return name;
        }
    }

}
