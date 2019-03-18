package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.service.AnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anonymize")
public class AnonymizationController {

    private AnonymizationService anonymizationService;

    @Autowired
    AnonymizationController(AnonymizationService anonymizationService){
        this.anonymizationService = anonymizationService;
    }

    @PostMapping
    public AnonymizationResultPayload anonymization(@RequestBody Request payload) {
        return anonymizationService.anonymize(payload);
    }


}
