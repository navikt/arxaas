package no.oslomet.aaas.model;

public class AttributeGeneralizationRow {
    final String name;
    final int generalizationLevel;

    public AttributeGeneralizationRow(String name, int generalizationLevel){
        this.name = name;
        this.generalizationLevel = generalizationLevel;
    }

    public String getName() {
        return name;
    }

    public int getGeneralizationLevel() {
        return generalizationLevel;
    }

}
