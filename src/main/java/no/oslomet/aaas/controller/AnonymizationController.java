package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.MetaData;
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

    @GetMapping
    public AnonymizationPayload anonymization(){
        AnonymizationPayload payload = new AnonymizationPayload();
        payload.setMetaData(new MetaData());
        return payload;
    }

    @PostMapping
    public AnonymizationResultPayload anonymization(@RequestBody AnonymizationPayload payload) {
        return anonymizationService.anonymize(payload);
    }


}
