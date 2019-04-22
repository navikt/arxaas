package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.exception.UnableToAnonymizeDataException;
import no.oslomet.aaas.exception.UnableToAnonymizeDataInvalidDataSetException;
import no.oslomet.aaas.model.anonymity.AnonymizeResult;
import no.oslomet.aaas.model.anonymity.AnonymizationMetrics;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.ConfigurationFactory;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Anonymizer class using the ARX library to implement the anonymization
 */
@Component
public class ARXAnonymizer implements Anonymizer {

    private final DataFactory dataFactory;
    private final ConfigurationFactory configFactory;
    private final Logger logger;

    @Autowired
    public ARXAnonymizer(DataFactory dataFactory, ConfigurationFactory configFactory) {
        this.dataFactory = dataFactory;
        this.configFactory = configFactory;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public AnonymizeResult anonymize(Request payload) {
        org.deidentifier.arx.ARXAnonymizer anonymizer = new org.deidentifier.arx.ARXAnonymizer();

        Data data = dataFactory.create(payload);
        try {
            ARXConfiguration config = configFactory.create(payload.getPrivacyModels(),payload.getSuppressionLimit());
            ARXResult result = anonymizer.anonymize(data,config);
            return packageResult(result,payload);
        } catch (IOException | NullPointerException e) {
            logger.error(String.format("Exception error: %s", e.toString()));
            throw new UnableToAnonymizeDataException(e.toString());
        } catch(IndexOutOfBoundsException e){
            String errorMessage = String.format("%s, Failed to create dataset. Check if dataset format and attribute dataset fields are correct", e.toString());
            logger.error(String.format("Exception error: %s", errorMessage));
            throw new UnableToAnonymizeDataInvalidDataSetException(errorMessage);
        }
    }

    /***
     * Returns an {@link AnonymizeResult} object containing a packaged results from the anonymized dataset
     * @param result an ARX {@link ARXResult} object containing the anonymized data and meta data
     * @param payload a {@link Request} object containing the dataset to be anonymized and the meta data to determine
     *               the settings and attributes on how to anonymized and analyze the data set
     * @return an {@link AnonymizeResult} object containing a packaged results from the anonymized dataset
     */
    private AnonymizeResult packageResult(ARXResult result, Request payload){
        List<String[]> anonymisedData = createRawDataList(result);
        AnonymizationMetrics attributeGeneralizationLevels = new AnonymizationMetrics(result);
        return new AnonymizeResult(anonymisedData, result.getGlobalOptimum().getAnonymity().toString(), attributeGeneralizationLevels,payload.getAttributes());
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
