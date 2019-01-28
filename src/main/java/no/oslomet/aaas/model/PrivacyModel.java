package no.oslomet.aaas.model;

public enum PrivacyModel {

    KANONYMITY("k-anonymity"),
    LDIVERSITY("l-diversity");

    private String name;

    PrivacyModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
