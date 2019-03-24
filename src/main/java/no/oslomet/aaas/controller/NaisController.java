package no.oslomet.aaas.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/nais")
public class NaisController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/isAlive")
    public String isAlive() {
        return "OK";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/isReady")
    public String isReady() {
        return "OK";
    }
}
