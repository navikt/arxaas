package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.DataType;

public class ARXDataType {

    private final Type type;

    @JsonCreator
    public ARXDataType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    DataType arxDataType(){
        switch (type){
            case STRING: return DataType.STRING;
            case ORDERED_STRING: return DataType.ORDERED_STRING;
            case INTEGER: return DataType.INTEGER;
            case DECIMAL: return DataType.DECIMAL;
            case DATE: return DataType.DATE;
        }
        throw new IllegalArgumentException("type=" + type.toString() + " is not supported");
    }

    public enum Type {
        INTEGER,
        STRING,
        DATE,
        ORDERED_STRING,
        DECIMAL
    }
}
