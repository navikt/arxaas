package no.nav.arxaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.model.*;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import no.nav.arxaas.service.AnonymizationService;
import no.nav.arxaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/anonymize/file")
public class FormDataAnonymizationController {

    private final AnonymizationService anonymizationService;
    private final LoggerService loggerService;

    @Autowired
    FormDataAnonymizationController(AnonymizationService anonymizationService, LoggerService loggerService) {
        this.anonymizationService = anonymizationService;
        this.loggerService = loggerService;
    }

    @PostMapping
    public AnonymizationResultPayload anonymization(@RequestParam("file") MultipartFile file, @RequestParam("payload") String payload, @RequestParam("hierarchies") MultipartFile[] hierarchies, HttpServletRequest request) {
        long requestRecivedTime = System.currentTimeMillis();
        Request requestPayload = buildRequestPayload(file, payload, hierarchies);
        loggerService.loggPayload(requestPayload, request.getRemoteAddr(), AnonymizationController.class);
        AnonymizationResultPayload anonymizationResult = anonymizationService.anonymize(requestPayload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnonymizeResult(anonymizationResult,requestProcessingTime, AnonymizationController.class, request.getRemoteAddr());
        return anonymizationService.anonymize(requestPayload);
    }

    private Request buildRequestPayload(MultipartFile file, String payload, MultipartFile[] hierarchies){
        List<String[]> rawData = handleInputStream(file);
        FormMetaDataRequest formMetaDataRequest = buildFormDataPayload(payload);
        List<Attribute> attributeList = buildRequestAttribute(formMetaDataRequest.getAttributes(),hierarchies);
        return new Request(rawData, attributeList, formMetaDataRequest.getPrivacyModels(), formMetaDataRequest.getSuppressionLimit());
    }

    private List<String[]> handleInputStream(MultipartFile file){
        List<String[]> rawData = new ArrayList<>();
        try {
            if(file != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    rawData.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData;
    }

    private FormMetaDataRequest buildFormDataPayload(String payload) {
        try {
            return new ObjectMapper().readValue(payload, FormMetaDataRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to create payload: " + e.getMessage());
        }
    }

    private List<Attribute> buildRequestAttribute(List<FormDataAttribute> attributeList, MultipartFile[] hierarchies){
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
