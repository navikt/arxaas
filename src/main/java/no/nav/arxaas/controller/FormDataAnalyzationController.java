package no.nav.arxaas.controller;

import no.nav.arxaas.model.*;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.service.AnalyzationService;
import no.nav.arxaas.service.LoggerService;
import no.nav.arxaas.utils.FormDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/analyze/file")
public class FormDataAnalyzationController {

    private final AnalyzationService analyzationService;
    private final LoggerService loggerService;
    private final FormDataFactory formDataFactory;

    @Autowired
    FormDataAnalyzationController(AnalyzationService analyzationService, LoggerService loggerService, FormDataFactory formDataFactory) {
        this.analyzationService = analyzationService;
        this.loggerService = loggerService;
        this.formDataFactory = formDataFactory;
    }

    @PostMapping
    public RiskProfile getPayloadAnalyzeFromFormData(@RequestPart("file") MultipartFile file, @RequestPart("metadata") @Valid FormMetaDataRequest metadata , HttpServletRequest request){
        long requestRecivedTime = System.currentTimeMillis();
        Request requestPayload = formDataFactory.createAnalyzationPayload(file,metadata);
        loggerService.loggPayload(requestPayload, request.getRemoteAddr(), AnalyzationController.class);
        RiskProfile analyzationResult = analyzationService.analyze(requestPayload);
        long requestProcessingTime = System.currentTimeMillis() - requestRecivedTime;
        loggerService.loggAnalyzationResult(analyzationResult, requestPayload, request.getRemoteAddr(), requestProcessingTime, AnalyzationController.class);
        return analyzationResult;
    }
}
