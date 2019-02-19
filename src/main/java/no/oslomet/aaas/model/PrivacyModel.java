package no.oslomet.aaas.model;

public enum PrivacyModel {

    KANONYMITY("K-Anonymity"),
    LDIVERSITY_DISTINCT("L-Diversity-Distinct"),
    LDIVERSITY_GRASSBERGERENTROPY("L-Diversity-Grassberger-Entropy"),
    LDIVERSITY_SHANNONENTROPY("L-Diversity-Shannon-Entropy"),
    LDIVERSITY_RECURSIVE("L-Diversity-Recursive");

    private String name;

    PrivacyModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
