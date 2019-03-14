package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.*;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Class responsible for converting data from the payload to a fully configured ARX Data object.
 */
@Component
public class ARXDataFactory implements DataFactory {

    @Override
    public Data create(AnonymizationPayload payload) {
        validateParameters(payload.getData(),payload.getMetaData());
        Data data = createData(payload.getData());
        setHierarchies(data,payload.getMetaData());
        setAttributeTypes(data,payload.getMetaData().getAttributeTypeList());

        return data;
    }


    @Override
    public Data create(Request payload) {
        validateParameters(payload.getData(),payload.getAttributes());
        Data data = createData(payload.getData());
        setHierarchies(data, payload.getAttributes());
        setAttributeTypes(data, payload.getAttributes());

        return data;
    }

    /***
     * Validation method for checking against invalid parameters for data anonymization
     * @param rawData an list of String[] containing tabular dataset
     * @param metaData a {@link MetaData} object containing parameters for anonymizing the dataset
     */
    private void validateParameters(List<String[]> rawData,MetaData metaData){
        if(rawData == null) throw new IllegalArgumentException("rawData parameter is null");
        if(metaData == null) throw new IllegalArgumentException("metaData parameter is null");
    }

    /***
     * Validation method for checking against invalid parameters for data analyzation
     * @param rawData an list of String[] containing tabular dataset
     * @param attributes a list of {@link Attribute} object containing parameters for dataset field attribute type and hierarchy
     */
    private void validateParameters(List<String[]> rawData, List<Attribute> attributes){
        if(rawData == null) throw new IllegalArgumentException("rawData parameter is null");
        if(attributes == null) throw new IllegalArgumentException("attributes parameter is null");
    }

    /***
     * Returns an ARX {@link Data} object created from the provided String. The object is a table of records/fields made from
     * the provided string.
     * @param rawData an list of String[] containing tabular dataset
     * @return the {@link Data} object created with the records/fields defined by the string of raw data
     */
    private Data createData(List<String[]> rawData) {
        return Data.create(rawData);
    }

    /***
     * Mutates an ARX {@link Data} object that holds the data set and assign an attribute type for each table row based
     * on the global {@link MetaData} metaData object.
     * @param data tabular data set to be anonymized
     * @param attributeTypes a map of string and {@link AttributeType} object containing parameters of dataset field attribute type
     */
    private void setAttributeTypes(Data data, Map<String, AttributeTypeModel> attributeTypes){
        for (Map.Entry<String, AttributeTypeModel> entry : attributeTypes.entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
    }

    private void setAttributeTypes(Data data, List<Attribute> attributes){
        for (Attribute attribute: attributes)
        {
            data.getDefinition().setAttributeType(attribute.getField(),
                    attribute.getAttributeTypeModel().getAttributeType());
        }
    }

    /**
     * Mutates an ARX {@link Data} object by setting the hierarchies to be used on the different fields in the data set
     * based on the global {@link MetaData} metaData object.
     * @param data tabular data set to be anonymize
     * @param metaData a {@link MetaData} object containing parameters for anonymizing the dataset
     */
    private void setHierarchies(Data data,MetaData metaData){
        for (Map.Entry<String, String[][]> entry : metaData.getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
    }

    private void setHierarchies(Data data, List<Attribute> attributeList){
        for (Attribute attribute : attributeList)
        {
            List<String[]> rawHierarchy = attribute.getHierarchy();
            if (rawHierarchy != null) {
                AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(rawHierarchy);
                data.getDefinition().setAttributeType(attribute.getField(), hierarchy);
            }
        }
    }

}
