package no.nav.arxaas.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * @return Resources object containing links to available resources in the service
     */
    @GetMapping()
    public CollectionModel<Object> root(){
        Link link_root = linkTo(this.getClass()).withSelfRel();
        Link link_anonymize = linkTo(AnonymizationController.class).withRel("anonymize");
        Link link_analyze = linkTo(AnalyzationController.class).withRel("analyze");
        return new CollectionModel(Collections.emptySet(), List.of(link_root, link_anonymize, link_analyze));
    }
}

