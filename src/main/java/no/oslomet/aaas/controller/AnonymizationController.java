package no.oslomet.aaas.controller;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.service.AnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/anonymize")
public class AnonymizationController {


    private AnonymizationService anonymizationService;

    @Autowired
    AnonymizationController(AnonymizationService anonymizationService){
        this.anonymizationService = anonymizationService;
    }


    @PostMapping
    public AnonymizationPayload anonymization(@RequestBody AnonymizationPayload payload){
        payload.setData("Viktor");
        return payload;
    }
}
