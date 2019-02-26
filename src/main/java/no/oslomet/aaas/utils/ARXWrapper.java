package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.AttributeTypeModel;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.*;

import org.deidentifier.arx.ARXResult;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Delivers an interface for reaching the underlying arx functionality of
 *
 */

@Component
public class ARXWrapper {

    public static final char CSV_SEPERATOR_CHAR = ',';
    final String COLUMNNAME = "column_name";

    /***
     * Returns an ARX {@link Data} object created from the provided String. The object is a table of records/fields made from
     * the provided string.
     * @param rawdata String containing tabular data set
     * @return the {@link Data} object created with the records/fields defined by the string of raw data
     */
    public Data setData(String rawdata) {
        Data data = null;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(rawdata.getBytes(StandardCharsets.UTF_8));
            data = Data.create(stream, Charset.defaultCharset(), ',');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * Sets the suppression limit configuration for anonymization in the
     * ARX {@link ARXConfiguration} object, then returns it.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymization/data set settings
     * @return an ARX {@link ARXConfiguration} object with the suppression setting
     */
    public ARXConfiguration setSuppressionLimit(ARXConfiguration config){
        config.setSuppressionLimit(0.02d);
        return config;
    }

    /***
     * Returns an ARX {@link Data} object that holds the data set along with an assigned attribute type for each table row.
     * @param data tabular data set to be anonymized
     * @param payload map containing parameters that defines the attribute types used on which data set field
     * @return an ARX {@link Data} that contains the data set with assigned field attribute types
     */
    public Data setSensitivityModels(Data data, AnonymizationPayload payload){
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
    public Data setSensitivityModels(Data data, AnalysationPayload analysationPayload){
        for (Map.Entry<String, AttributeTypeModel> entry : analysationPayload.getAttributeTypes().entrySet())
        {
            data.getDefinition().setAttributeType(entry.getKey(),entry.getValue().getAttributeType());
        }
        return data;
    }

    /***
     * Returns an ARX {@link ARXConfiguration} object that sets the privacy models defined by the payload.
     * @param config an ARX {@link ARXConfiguration} object that holds the anonymize/data set settings
     * @param payload map containing parameters that defines the privacy models to be used
     * @return an ARX {@link ARXConfiguration} object with the assigned privacy models settings
     */
    public ARXConfiguration setPrivacyModels(ARXConfiguration config, AnonymizationPayload payload){
        for (Map.Entry<PrivacyModel, Map<String,String>> entry : payload.getMetaData().getModels().entrySet())
        {
            config.addPrivacyModel(getPrivacyModel(entry.getKey(),entry.getValue()));
        }
        return config;
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

    /**
     * Returns an Arx {@link PrivacyCriterion} object for the desired privacy object selected by the user.
     * @param model  enum representing the privacy model type we want created
     * @param params map containing parameters that defines which settings to be used to created the privacy model
     * @return the {@link PrivacyCriterion} object created with the specified parameters
     */
    public PrivacyCriterion getPrivacyModel(PrivacyModel model, Map<String,String> params){
      switch(model){
          case KANONYMITY:
              return new KAnonymity(Integer.parseInt(params.get("k")));
          case LDIVERSITY_DISTINCT:
              return new DistinctLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")));
          case LDIVERSITY_SHANNONENTROPY:
              return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                      EntropyLDiversity.EntropyEstimator.SHANNON);
          case LDIVERSITY_GRASSBERGERENTROPY:
              return new EntropyLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                      EntropyLDiversity.EntropyEstimator.GRASSBERGER);
          case LDIVERSITY_RECURSIVE:
              return new RecursiveCLDiversity(params.get(COLUMNNAME),Integer.parseInt(params.get("l")),
                      Integer.parseInt(params.get("c")));
          default:
              throw new RuntimeException(model.getName() + " Privacy Model not supported");
      }
    }

    /***
     * Returns an ARX {@link ARXAnonymizer} objects that sets the settings for anonymizing the data set.
     * @param anonymizer an ARX {@link ARXAnonymizer} object that will hold the anonymization settings
     * @return an ARX {@link ARXAnonymizer} object that holds the anonymization settings
     */
    public ARXAnonymizer setAnonymizer(ARXAnonymizer anonymizer){
        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        return  anonymizer;
    }

        //remeber we need data parameter

    /***
     * Returns an ARX {@link ARXResult} object that holds the anonymized data set. This method uses the payload
     * parameter to create the ARX {@link Data} object, and sets the attribute types for each field in the data set.
     * The method will then use that ARX {@link Data} object along with the defined settings,
     * taken from the ARX {@link ARXAnonymizer} and {@link ARXConfiguration} objects to create the anonymized data set.
     * @param anonymizer an ARX {@link ARXAnonymizer} object that will hold the anonymization settings
     * @param config an ARX {@link ARXConfiguration} object that will hold the anonymize/data set settings
     * @param payload map containing parameters that sets the records/fields for the data set and the parameters used to
     *                anonymize and analyse the data set
     * @return an ARX {@link ARXResult} object that holds the anonymized data set
     * @throws IOException that shows the error message when anonymizing the data set fails
     */
    public ARXResult anonymize(ARXAnonymizer anonymizer, ARXConfiguration config, AnonymizationPayload payload) throws IOException {
        Data data = setData(payload.getData());
        data = setSensitivityModels(data,payload);
        data = setHierarchies(data, payload);
        config = setSuppressionLimit(config);
        config = setPrivacyModels(config,payload);
        anonymizer = setAnonymizer(anonymizer);
        return anonymizer.anonymize(data,config);
    }

    /***
     * Returns a String that contains the anonymized data. This method is used to prepare the data set to be easily
     * read and imported in the response payload.
     * @param result an ARX {@link ARXResult} object that holds the anonymized data set.
     * @return a String containing the anonymized data set.
     * @throws IOException shows the error message when saving the data set to a {@link ByteArrayInputStream} object fails
     */
    public String getAnonymizeData(ARXResult result) throws IOException {
        DataHandle handle = result.getOutput();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handle.save(outputStream, CSV_SEPERATOR_CHAR);
        return new String(outputStream.toByteArray());
    }
}
