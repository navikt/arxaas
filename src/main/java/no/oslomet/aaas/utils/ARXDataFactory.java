package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Class responsible for converting data from the payload to a fully configured ARX Data object.
 */
public class ARXDataFactory {

    private final String rawData;
    private final MetaData metaData;
    private static final char DEFAULT_CSV_SEPERATOR =',';

    ARXDataFactory(String rawData,MetaData metaData){
        this.rawData = rawData;
        this.metaData = metaData;
        validateParameters(rawData,metaData);
    }

    public Data create(){
        Data data = readDataString();
        setAttributeTypes(data);
        setHierarchies(data);

        return data;
    }

    private void validateParameters(String rawData,MetaData metaData){
        if(rawData == null) throw new IllegalArgumentException("rawData parameter is null");
        if(metaData == null) throw new IllegalArgumentException("metaData parameter is null");
    }

    /***
     * Returns an ARX {@link Data} object created from the provided String. The object is a table of records/fields made from
     * the provided string.
     * @return the {@link Data} object created with the records/fields defined by the string of raw data
     */
    private Data readDataString() {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.UTF_8));
            return Data.create(stream, Charset.defaultCharset(), DEFAULT_CSV_SEPERATOR);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /***
     * Mutates an ARX {@link Data} object that holds the data set and assign an attribute type for each table row based
     * on the global {@link MetaData} metaData object.
     * @param data tabular data set to be anonymized
     */
    private void setAttributeTypes(Data data){
        for (Map.Entry<String, AttributeTypeModel> entry : metaData.getAttributeTypeList().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
    }

    /**
     * Mutates an ARX {@link Data} object by setting the hierarchies to be used on the different fields in the data set
     * based on the global {@link MetaData} metaData object.
     * @param data tabular data set to be anonymize
     */
    private void setHierarchies(Data data){
        for (Map.Entry<String, String[][]> entry : metaData.getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
    }

}
