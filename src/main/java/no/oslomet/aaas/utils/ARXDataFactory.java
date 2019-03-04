package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ARXDataFactory {

    private final String rawData;
    private final MetaData metaData;
    private static final char CSV_SEPERATOR_CHAR = ',';

    ARXDataFactory(String rawData,MetaData metaData){
        this.rawData = rawData;
        this.metaData = metaData;
        //todo validation?
    }

    public Data create(){
        Data data = readDataString();
        setAttributeTypes(data);
        setHierarchies(data);

        return data;
    }

    //todo UPDATE javadoc
    /***
     * Returns an ARX {@link Data} object created from the provided String. The object is a table of records/fields made from
     * the provided string.
     * @return the {@link Data} object created with the records/fields defined by the string of raw data
     */
    private Data readDataString() {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.UTF_8));
            return Data.create(stream, Charset.defaultCharset(), CSV_SEPERATOR_CHAR);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    //todo UPDATE javadoc
    /***
     * Returns an ARX {@link Data} object that holds the data set along with an assigned attribute type for each table row.
     * @param data tabular data set to be anonymized
     * @return an ARX {@link Data} that contains the data set with assigned field attribute types
     */
    private void setAttributeTypes(Data data){
        for (Map.Entry<String, AttributeTypeModel> entry : metaData.getAttributeTypeList().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
    }

    //todo UPDATE javadoc
    /**
     * Returns an ARX {@link Data} object that sets the hierarchies to be used on the different fields in the data set.
     * @param data tabular data set to be anonymize
     * @return an ARX {@link Data} object with the hierarchies assigned to the data set fields
     */
    public void setHierarchies(Data data){
        for (Map.Entry<String, String[][]> entry : metaData.getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
    }

}
