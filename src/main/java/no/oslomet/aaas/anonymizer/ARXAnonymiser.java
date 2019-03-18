package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.exception.UnableToAnonymizeDataException;
import no.oslomet.aaas.model.AnonymizeResult;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.ConfigurationFactory;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Anonymizer class using the ARX library to implement the anonymization
 */
@Component
public class ARXAnonymiser implements Anonymiser {

    private final DataFactory dataFactory;
    private final ConfigurationFactory configFactory;

    @Autowired
    public ARXAnonymiser( DataFactory dataFactory, ConfigurationFactory configFactory) {
        this.dataFactory = dataFactory;
        this.configFactory = configFactory;
    }

    @Override
    public AnonymizeResult anonymize(Request payload) {
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        configureAnonymizer(anonymizer);

        Data data = dataFactory.create(payload);
        ARXConfiguration config = configFactory.create(payload.getPrivacyModels());
        try {
            ARXResult result = anonymizer.anonymize(data,config);
            List<String[]> anonymisedData = createRawDataList(result);
            return new AnonymizeResult(anonymisedData, result.getGlobalOptimum().getAnonymity().toString(), null);
        } catch (IOException e) {
            throw new UnableToAnonymizeDataException(e.getMessage());
        }
    }

    /***
     * Returns an ARX {@link ARXAnonymizer} objects that sets the settings for anonymizing the data set.
     * @param anonymizer an ARX {@link ARXAnonymizer} object that will hold the anonymization settings
     */
    private void configureAnonymizer(ARXAnonymizer anonymizer){
        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
    }

    /***
     * Returns a String that contains the anonymized data. This method is used to prepare the data set to be easily
     * read and imported in the response payload.
     * @param result an ARX {@link ARXResult} object that holds the anonymized data set.
     * @return a String containing the anonymized data set.
     */
    private List<String[]> createRawDataList(ARXResult result){
        DataHandle handle = result.getOutput();
        List<String[]> resultData =  new ArrayList<>();
        handle.iterator().forEachRemaining(resultData::add);
        return resultData;
    }
}
