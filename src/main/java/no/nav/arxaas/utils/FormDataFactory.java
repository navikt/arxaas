package no.nav.arxaas.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.FormDataAttribute;
import no.nav.arxaas.model.FormMetaDataRequest;
import no.nav.arxaas.model.Request;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Public contract to be fulfilled by FormDataAnonymization and FormDataAnalysation classes
 */
public class FormDataFactory {

    /***
     * Returns an ARX {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset and metadata.
     */
    public Request createAnalyzationPayload(MultipartFile file, String payload){
        validateParameters(file,payload);
        List<String[]> rawData = handleInputStream(file);
        FormMetaDataRequest formMetaDataRequest = buildFormDataPayload(payload);
        List<Attribute> attributeList = buildRequestAnalyzationAttribute(formMetaDataRequest.getAttributes());
        return new Request(rawData, attributeList, formMetaDataRequest.getPrivacyModels(), formMetaDataRequest.getSuppressionLimit());
    }

    /***
     * Returns an ARX {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @param hierarchies a array of {@link MultipartFile} containing the raw hierarchy files to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset, metadata and hierarchies.
     */
    public Request createAnonymizationPayload(MultipartFile file, String payload, MultipartFile[] hierarchies){
        validateParameters(file,payload);
        List<String[]> rawData = handleInputStream(file);
        FormMetaDataRequest formMetaDataRequest = buildFormDataPayload(payload);
        List<Attribute> attributeList = buildRequestAnonymizationAttribute(formMetaDataRequest.getAttributes(),hierarchies);
        return new Request(rawData, attributeList, formMetaDataRequest.getPrivacyModels(), formMetaDataRequest.getSuppressionLimit());
    }

    /***
     * Validation method for checking against invalid parameters for data analyzation and anonymization
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     */
    private void validateParameters(MultipartFile file, String payload){
        if(file == null) throw new IllegalArgumentException("file parameter is null");
        if(payload == null) throw new IllegalArgumentException("metadata payload parameter is null");
    }

    /***
     * Returns a list of String[] containing the dataset parsed from a {@link MultipartFile}.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @return the parsed dataset in a list of String[].
     */
    private List<String[]> handleInputStream(MultipartFile file){
        List<String[]> rawData = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                rawData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData;
    }

    /***
     * Builds a {@link FormMetaDataRequest} model from the string payload.
     * @param payload a String containing the raw metadata to be parsed.
     * @return a {@link FormMetaDataRequest} model containing the metadata for analyzation and anonymization configurations.
     */
    private FormMetaDataRequest buildFormDataPayload(String payload){
        try {
            return new ObjectMapper().readValue(payload, FormMetaDataRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to create payload: " + e.getMessage());
        }
    }

    /***
     * Builds a list of {@link Attribute} from the  list of {@link FormDataAttribute} to correctly build the attribute metadata.
     * @param attributeList a list of {@link FormDataAttribute} to be parsed in order to correctly build the attribute metadata.
     * @return list of {@link Attribute} for analyzation.
     */
    private List<Attribute> buildRequestAnalyzationAttribute(List<FormDataAttribute> attributeList){
        List<Attribute> newAttributeList = new ArrayList<>();
        attributeList.forEach(attribute -> {
            Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), null);
            newAttributeList.add(newAttribute);
        });
        return newAttributeList;
    }

    /***
     * Builds a list of {@link Attribute} from the  list of {@link FormDataAttribute} to correctly build the attribute metadata
     * and parse the raw hierarchy files.
     * @param attributeList a list of {@link FormDataAttribute} to be parsed in order to correctly build the attribute metadata.
     * @param hierarchies a array of {@link MultipartFile} containing the raw hierarchy files to be parsed.
     * @return list of {@link Attribute} for anonymization.
     */
    private List<Attribute> buildRequestAnonymizationAttribute(List<FormDataAttribute> attributeList, MultipartFile[] hierarchies){
        List<Attribute> newAttributeList = new ArrayList<>();

        attributeList.forEach(attribute -> {
            if(attribute.getHierarchy() != null) {
                int hierarchyIndex = attribute.getHierarchy();
                List<String[]> hierarchy = handleInputStream(hierarchies[hierarchyIndex]);
                Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), hierarchy);
                newAttributeList.add(newAttribute);
            }else {
                Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), null);
                newAttributeList.add(newAttribute);
            }
        });
        return newAttributeList;
    }
}
