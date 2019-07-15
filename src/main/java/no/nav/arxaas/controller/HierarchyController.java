package no.nav.arxaas.controller;


import no.nav.arxaas.hierarchy.Hierarchy;
import no.nav.arxaas.hierarchy.HierarchyRequest;

import no.nav.arxaas.service.HierarchyService;
import no.nav.arxaas.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/hierarchy")
public class HierarchyController {

    private final LoggerService loggerService;
    private final HierarchyService hierarchyService;

    @Autowired
    HierarchyController(HierarchyService hierarchyService, LoggerService loggerService) {
        this.hierarchyService = hierarchyService;
        this.loggerService = loggerService;
    }


    @PostMapping
    public Hierarchy hierarchy(@Valid @RequestBody HierarchyRequest hierarchyRequest, HttpServletRequest request) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Hierarchy request: builder=" + hierarchyRequest.getBuilder().toString());
        return hierarchyService.hierarchy(hierarchyRequest);
    }
}
