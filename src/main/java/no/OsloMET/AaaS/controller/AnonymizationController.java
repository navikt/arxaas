package no.OsloMET.AaaS.controller;

import no.OsloMET.AaaS.service.AnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AnonymizationController {

    @Autowired
    AnonymizationService anonymizationService;

    @RequestMapping("/")
    public String presetKAnonymization(){
        System.out.println("Triggered");
        anonymizationService.sihei();
        return "Successfull";
    }
}
