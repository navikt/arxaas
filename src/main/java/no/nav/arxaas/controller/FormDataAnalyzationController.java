package no.nav.arxaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.model.*;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.service.AnalyzationService;
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
@RequestMapping("/api/analyze/file")
public class FormDataAnalyzationController {

    private final AnalyzationService analyzationService;
    private final LoggerService loggerService;

    @Autowired
    FormDataAnalyzationController(AnalyzationService analyzationService, LoggerService loggerService) {
        this.analyzationService = analyzationService;
        this.loggerService = loggerService;
    }

    @PostMapping
    public RiskProfile getPayloadAnalyzeFromFormData(@RequestParam("file") MultipartFile file, @RequestParam("payload") String payload, HttpServletRequest request){
        long requestRecivedTime = System.currentTimeMillis();
        Request requestPayload = buildRequestPayload(file,payload);
        loggerService.loggPayload(requestPayload, request.getRemoteAddr(), AnalyzationController.class);
        RiskProfile analyzationResult = analyzationService.analyze(requestPayload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnalyzationResult(analyzationResult, requestPayload, request.getRemoteAddr(), requestProcessingTime, AnalyzationController.class);
        return analyzationResult;
    }

    private Request buildRequestPayload(MultipartFile file, String payload){
        List<String[]> rawData = handleInputStream(file);
        FormMetaDataRequest formMetaDataRequest = buildFormDataPayload(payload);
        List<Attribute> attributeList = buildRequestAttribute(formMetaDataRequest.getAttributes());
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

    private FormMetaDataRequest buildFormDataPayload(String payload){
        try {
            return new ObjectMapper().readValue(payload, FormMetaDataRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to create payload: " + e.getMessage());
        }
    }

    private List<Attribute> buildRequestAttribute(List<FormDataAttribute> attributeList){
        List<Attribute> newAttributeList = new ArrayList<>();
        attributeList.forEach(attribute -> {
            Attribute newAttribute = new Attribute(attribute.getField(), attribute.getAttributeTypeModel(), null);
            newAttributeList.add(newAttribute);
        });
        return newAttributeList;
    }
}
