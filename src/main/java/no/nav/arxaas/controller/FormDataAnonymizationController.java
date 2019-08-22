package no.nav.arxaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.model.FormDataRequest;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.model.anonymity.AnonymizationResultPayload;
import no.nav.arxaas.service.AnonymizationService;
import no.nav.arxaas.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public AnonymizationResultPayload anonymization(@RequestParam("file") MultipartFile file,@Valid @RequestParam("payload") String payload, HttpServletRequest request) {
        long requestRecivedTime = System.currentTimeMillis();
        Request requestPayload = buildRequestPayload(file,payload);
        loggerService.loggPayload(requestPayload, request.getRemoteAddr(), AnonymizationController.class);
        AnonymizationResultPayload anonymizationResult = anonymizationService.anonymize(requestPayload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnonymizeResult(anonymizationResult,requestProcessingTime, AnonymizationController.class, request.getRemoteAddr());
        return anonymizationService.anonymize(requestPayload);
    }

    private Request buildRequestPayload(MultipartFile file, String payload){
        List<String[]> rawData = handleInputStream(file);
        FormDataRequest formDataRequest = buildFormDataPayload(payload);
        return new Request(rawData, formDataRequest.getAttributes(), formDataRequest.getPrivacyModels(), formDataRequest.getSuppressionLimit());
    }

    private List<String[]> handleInputStream(MultipartFile file){
        List<String[]> rawData = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) != null ){
                String[] data = line.split(";");
                rawData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData;
    }

    private FormDataRequest buildFormDataPayload(String payload) {
        try {
            return new ObjectMapper().readValue(payload, FormDataRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to create payload: " + e.getMessage());
        }
    }
}
