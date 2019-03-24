package no.oslomet.aaas.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/nais")
public class NaisController {

    @GetMapping(path = "/isAlive")
    public String isAlive() {
        return "OK";
    }

    @GetMapping(path = "/isReady")
    public String isReady() {
        return "OK";
    }
}
