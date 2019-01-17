package no.OsloMET.AaaS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AnonymizationController {

    @RequestMapping("/")
    public String presetKAnonymization(){
        System.out.println("Triggered");
        return "Successfull";
    }
}
