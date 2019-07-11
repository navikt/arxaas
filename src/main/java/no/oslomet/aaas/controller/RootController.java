package no.oslomet.aaas.controller;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Controller serving available resources for the service
 */
@Controller
@RequestMapping(path = "/")
public class RootController {

    @RequestMapping("/")
    public String root() {
        return "redirect:index.html";
    }
}
