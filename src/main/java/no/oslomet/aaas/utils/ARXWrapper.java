package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import org.deidentifier.arx.*;

import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Delivers an interface for reaching the underlying arx functionality of
 *
 */

@Component
public class ARXWrapper {

    private ARXConfigurationSetter arxConfigurationSetter;

    @Autowired
    public ARXWrapper(ARXConfigurationSetter arxConfigurationSetter){
        this.arxConfigurationSetter = arxConfigurationSetter;
    }


    /***
     * Returns an ARX {@link Data} object created from the provided String. The object is a table of records/fields made from
     * the provided string.
     * @param rawData List of String array that contains tabular data set
     * @return the {@link Data} object created with the records/fields defined by the string of raw data
     */
    Data createData(List<String[]> rawData) {
        return Data.create(rawData);
    }

    /***
     * Returns an ARX {@link ARXAnonymizer} objects that sets the settings for anonymizing the data set.
     * @param anonymizer an ARX {@link ARXAnonymizer} object that will hold the anonymization settings
     * @return an ARX {@link ARXAnonymizer} object that holds the anonymization settings
     */
    ARXAnonymizer setAnonymizer(ARXAnonymizer anonymizer){
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
    public ARXResult anonymize(ARXAnonymizer anonymizer, ARXConfiguration config, AnonymizationPayload payload, Data data) throws IOException {
        config = arxConfigurationSetter.setSuppressionLimit(config);
        config = arxConfigurationSetter.setPrivacyModels(config,payload);
        anonymizer = setAnonymizer(anonymizer);
        return anonymizer.anonymize(data,config);
    }

    /***
     * Returns a String that contains the anonymized data. This method is used to prepare the data set to be easily
     * read and imported in the response payload.
     * @param result an ARX {@link ARXResult} object that holds the anonymized data set.
     * @return a String containing the anonymized data set.
     */
    public List<String[]> getAnonymizeData(ARXResult result){
        DataHandle handle = result.getOutput();
        List<String[]> resultData =  new ArrayList<>();
        handle.iterator().forEachRemaining(resultData::add);
        return resultData;
    }
}
