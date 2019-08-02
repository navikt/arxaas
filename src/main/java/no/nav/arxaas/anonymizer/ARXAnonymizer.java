package no.nav.arxaas.anonymizer;

import no.nav.arxaas.exception.UnableToAnonymizeDataException;
import no.nav.arxaas.exception.UnableToAnonymizeDataInvalidDataSetException;
import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.model.anonymity.AnonymizationMetrics;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.ConfigurationFactory;
import no.nav.arxaas.utils.DataFactory;
import org.deidentifier.arx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Anonymizer class using the ARX library to implement the arxaas
 */
@Component
public class ARXAnonymizer implements Anonymizer {

    private final DataFactory dataFactory;
    private final ConfigurationFactory configFactory;
    private final Logger logger;
    private static final String exceptionError = "Exception error: %s";

    @Autowired
    public ARXAnonymizer(DataFactory dataFactory, ConfigurationFactory configFactory) {
        this.dataFactory = dataFactory;
        this.configFactory = configFactory;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Method to run arxaas on data in the payload with the provided parameters in the payload
     * @param payload {@link Request} object containing the data to be anonymized and params to use in arxaas
     * @return an {@link AnonymizeResult} object containing the best case arxaas and statistics
     */
    @Override
    public AnonymizeResult anonymize(Request payload) {
        Data data = dataFactory.create(payload);
        ARXConfiguration config = getARXConfiguration(payload);
        ARXResult result = getARXResult(data, config);
        return packageResult(result,payload);
    }

    /***
     * Returns an {@link ARXResult} object containing the anonymized dataset based on the arxaas settgings provided
     * which dataset to anonymize.
     * @param data a {@link Data} object to be anonymized
     * @param config an {@link ARXConfiguration} object containing the settings on how to anonymize the data
     * @return an {@link ARXResult} object containing the anonymized dataset.
     */
    private ARXResult getARXResult(Data data, ARXConfiguration config) {
        org.deidentifier.arx.ARXAnonymizer anonymizer = new org.deidentifier.arx.ARXAnonymizer();
        try{
            ARXResult result = anonymizer.anonymize(data, config);
            if(result.getOutput() == null){
                String errorMessage = "Could not fulfill the privacy criterion set";
                throw new UnableToAnonymizeDataException(errorMessage);
            }
            return result;
        }catch(IOException | IndexOutOfBoundsException e){
            String errorMessage = String.format("%s, Failed to create dataset. Check if dataset format and attribute dataset fields are correct", e.toString());
            throw new UnableToAnonymizeDataInvalidDataSetException(errorMessage);
        }
    }

    /***
     * Retruns an {@link ARXConfiguration} object containing the arxaas settings defined by the request payload.
     * @param payload a {@link Request} object containing the settings to be applied when anonymizing the dataset
     * @return  an {@link ARXConfiguration} object containing the settings on how to anonymized the dataset
     */
    private ARXConfiguration getARXConfiguration(Request payload) {
        try{
            return configFactory.create(payload.getPrivacyModels(),payload.getSuppressionLimit());
        }catch (IndexOutOfBoundsException | NullPointerException e){
            String errorMessage = String.format("%s, Failed to create dataset. Check if dataset format and attribute dataset fields are correct", e.toString());
            logger.error(String.format(exceptionError, errorMessage));
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
