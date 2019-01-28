package no.oslomet.aaas.model;

public enum PrivacyModel {

    KANONYMITY("K-Anonymity"),
    LDIVERSITY("L-Diversity");

    private String name;

    PrivacyModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
