package no.oslomet.aaas.model;

public class AttributeGeneralizationRow {
    final String name;
    final String type;
    final int generalizationLevel;

    public AttributeGeneralizationRow(String name, String type, int generalizationLevel){
        this.name = name;
        this.type = type;
        this.generalizationLevel = generalizationLevel;
    }

    public String getName() {
        return name;
    }

    public int getGeneralizationLevel() {
        return generalizationLevel;
    }

    public String getType() {
        return type;
    }
}
