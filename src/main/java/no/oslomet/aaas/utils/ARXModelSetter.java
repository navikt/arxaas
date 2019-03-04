package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ARXModelSetter {
    /***
     * Returns an ARX {@link Data} object that holds the data set along with an assigned attribute type for each table row.
     * @param data tabular data set to be anonymized
     * @param payload map containing parameters that defines the attribute types used on which data set field
     * @return an ARX {@link Data} that contains the data set with assigned field attribute types
     */
    public Data setAttributeTypes(Data data, AnonymizationPayload payload){
        for (Map.Entry<String, AttributeTypeModel> entry : payload.getMetaData().getAttributeTypeList().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
        return data;
    }

    /***
     * Returns an ARX {@link Data} object that holds the data set along with an assigned attribute type for each table row.
     * @param data tabular data set to be analysied for re-identification risk
     * @param analysationPayload map containing parameters that defines the attribute types used on which data set field
     * @return an ARX {@link Data} that contains the data set with assigned field attribute types
     */
    public Data setAttributeTypes(Data data, AnalysationPayload analysationPayload){
        for (Map.Entry<String, AttributeTypeModel> entry : analysationPayload.getAttributeTypes().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
        return data;
    }

    /**
     * Returns an ARX {@link Data} object that sets the hierarchies to be used on the different fields in the data set.
     * @param data tabular data set to be anonymized
     * @param payload map containing parameters that defines the hierarchies to be used on which data set fields
     * @return an ARX {@link Data} object with the hierarchies assigned to the data set fields
     */
    public Data setHierarchies(Data data, AnonymizationPayload payload){
        for (Map.Entry<String, String[][]> entry : payload.getMetaData().getHierarchy().entrySet())
        {
            AttributeType.Hierarchy hierarchy = AttributeType.Hierarchy.create(entry.getValue());
            data.getDefinition().setAttributeType(entry.getKey(),hierarchy);
        }
        return data;
    }
}
