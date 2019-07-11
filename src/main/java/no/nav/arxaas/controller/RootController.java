package no.nav.arxaas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller serving available resources for the service
 */
@Controller
@RequestMapping(path = "/")
public class RootController {

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping("/")
    public String root() {
        return "redirect:index.html";
    }

}
