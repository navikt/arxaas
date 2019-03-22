package no.oslomet.aaas.controller;
import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnonymizationService;
import no.oslomet.aaas.service.LoggerAnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/anonymize")
public class AnonymizationController {

    private  final AnonymizationService anonymizationService;
    private  final LoggerAnonymizationService loggerAnonymizationService;

    @Autowired
    AnonymizationController(AnonymizationService anonymizationService, LoggerAnonymizationService loggerAnonymizationService){
        this.anonymizationService = anonymizationService;
        this.loggerAnonymizationService = loggerAnonymizationService;
    }


    @PostMapping
    public AnonymizationResultPayload anonymization(@RequestBody Request payload) {
        loggerAnonymizationService.loggAnonymizationPayload(payload);
        return anonymizationService.anonymize(payload);
    }

}
