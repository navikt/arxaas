package no.nav.arxaas.utils;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import no.nav.arxaas.model.Attribute;
import no.nav.arxaas.model.FormDataAttribute;
import no.nav.arxaas.model.FormMetaDataRequest;
import no.nav.arxaas.model.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Public contract to be fulfilled by FormDataAnonymization and FormDataAnalysation classes
 */
@Component
public class FormDataFactory {

    /***
     * Returns an {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset and metadata.
     * @throws IOException when unable to read the InputStream of the files
     */
    public Request createAnalyzationPayload(MultipartFile file, FormMetaDataRequest payload) throws IOException {
        validateParameters(file,payload);
        List<String[]> rawData = handleInputStream(file);
        List<Attribute> attributeList = buildRequestAnalyzationAttribute(payload.getAttributes());
        return new Request(rawData, attributeList, payload.getPrivacyModels(), payload.getSuppressionLimit());
    }

    /***
     * Returns an {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @param hierarchies a array of {@link MultipartFile} containing the raw hierarchy files to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset, metadata and hierarchies.
     * @throws IOException when unable to read the InputStream of the files
     */
    public Request createAnonymizationPayload(MultipartFile file, FormMetaDataRequest payload, MultipartFile[] hierarchies) throws IOException {
        validateParameters(file,payload);
        List<String[]> rawData = handleInputStream(file);
        List<List<String[]>> hierarchiesContent = new ArrayList<>();
        if(hierarchies.length > 0) {
            hierarchiesContent = handleHierarchiesInputStream(hierarchies);
        }
        List<Attribute> attributeList = buildRequestAnonymizationAttribute(payload.getAttributes(),hierarchiesContent);
        return new Request(rawData, attributeList, payload.getPrivacyModels(), payload.getSuppressionLimit());
    }

    /***
     * Validation method for checking against invalid parameters for data analyzation and anonymization
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     */
    private void validateParameters(MultipartFile file, FormMetaDataRequest payload){
        if(file == null) throw new IllegalArgumentException("file parameter is null");
        if(payload == null) throw new IllegalArgumentException("metadata payload parameter is null");
    }

    /***
     * Returns a list of String[] containing the dataset parsed from a {@link MultipartFile}.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @return the parsed dataset in a list of String[].
     * @throws IOException when unable to read the InputStream of the files
     */
    private List<String[]> handleInputStream(MultipartFile file) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true,';',',');
        CsvParser parser = new CsvParser(settings);
        InputStream fileContent = file.getInputStream();
        List<String[]> rawData = parser.parseAll(fileContent);
        fileContent.close();
        return rawData;
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
     * @param hierarchies a List containing the parsed hierarchy files. the hierarchy files are in a List of String array
     * @return list of {@link Attribute} for anonymization.
     */
    private List<Attribute> buildRequestAnonymizationAttribute(List<FormDataAttribute> attributeList, List<List<String[]>> hierarchies){
        List<Attribute> newAttributeList = new ArrayList<>();
        attributeList.forEach(attribute -> {
            if(attribute.getHierarchy() != null && hierarchies.size() > 0) {
                int hierarchyIndex = attribute.getHierarchy();
                List<String[]> hierarchy = hierarchies.get(hierarchyIndex);
                Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), hierarchy);
                newAttributeList.add(newAttribute);
            }else {
                Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), null);
                newAttributeList.add(newAttribute);
            }
        });
        return newAttributeList;
    }

    /***
     * Takes an array of {@link MultipartFile} containing the raw hierarchy files, and parses through the files and building a list out of them.
     * @param hierarchies an array of {@link MultipartFile} containing the raw hierarchy files to be parsed.
     * @return a List of parsed hierarchy files in the format of List of String array
     * @throws IOException when unable to read the InputStream of the files
     */
    private List<List<String[]>> handleHierarchiesInputStream(MultipartFile[] hierarchies) throws IOException {
        List<List<String[]>> hierarchiesContent = new ArrayList<>();
        for(int x = 0; x<hierarchies.length; x++){
            List<String[]> hierarchyContent = handleInputStream(hierarchies[x]);
            hierarchiesContent.add(x,hierarchyContent);
        }
        return hierarchiesContent;
    }
}
