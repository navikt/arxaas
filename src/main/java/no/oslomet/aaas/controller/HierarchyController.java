package no.oslomet.aaas.controller;


import no.oslomet.aaas.hierarchy.Hierarchy;
import no.oslomet.aaas.hierarchy.HierarchyRequest;

import no.oslomet.aaas.service.HierarchyService;
import no.oslomet.aaas.service.LoggerService;
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
