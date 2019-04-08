package no.oslomet.aaas.model;

import org.deidentifier.arx.criteria.TCloseness;

import java.util.Map;

public class PrivacyCriterionModel {

    private final PrivacyModel privacyModel;
    private final Map<String, String> params;

    public PrivacyCriterionModel(PrivacyModel privacyModel, Map<String, String> params) {
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
     * Model enum for PrivacyCriterionModel. Designates the Privacy model to be used in the anonymization process.
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
