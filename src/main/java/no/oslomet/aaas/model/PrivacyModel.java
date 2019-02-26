package no.oslomet.aaas.model;

/***
 * Model class for Privacy model. Sets the Privacy model for the anonymization process.
 */
public enum PrivacyModel {

    KANONYMITY("K-Anonymity"),
    LDIVERSITY_DISTINCT("L-Diversity-Distinct"),
    LDIVERSITY_GRASSBERGERENTROPY("L-Diversity-Grassberger-Entropy"),
    LDIVERSITY_SHANNONENTROPY("L-Diversity-Shannon-Entropy"),
    LDIVERSITY_RECURSIVE("L-Diversity-Recursive");

    private String name;

    /***
     * Setter method for the privacy medel.l
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
