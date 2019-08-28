package no.nav.arxaas.utils;

import no.nav.arxaas.model.Request;
import org.springframework.web.multipart.MultipartFile;

/**
 * Public contract to be fulfilled by FormDataAnonymization and FormDataAnalysation classes
 */
public interface FormDataFactory {

    /***
     * Returns an ARX {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset and metadata.
     */
    Request createAnalyzationPayload(MultipartFile file, String payload);

    /***
     * Returns an ARX {@link Request} object created from the provided MultipartFile and String.
     * @param file a {@link MultipartFile} containing the raw dataset file to be parsed.
     * @param payload a String containing the raw metadata to be parsed.
     * @param hierarchies a array of {@link MultipartFile} containing the raw hierarchy files to be parsed.
     * @return an ARX {@link Request} model containing the parsed dataset, metadata and hierarchies.
     */
    Request createAnonymizationPayload(MultipartFile file, String payload, MultipartFile[] hierarchies);
}
