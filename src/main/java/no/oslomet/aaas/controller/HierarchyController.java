package no.oslomet.aaas.controller;

import no.oslomet.aaas.hierarchies.ARXHierarchy;
import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.hierarchy.Hierarchy;
import no.oslomet.aaas.model.hierarchy.HierarchyRequest;
import no.oslomet.aaas.service.AnonymizationService;
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
    public Hierarchy hierarchy(@RequestBody HierarchyRequest hierarchyRequest, HttpServletRequest request) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Hierarchy request: " + hierarchyRequest.getBuilder().toString());
        return hierarchyService.hierarchy(hierarchyRequest);
    }
}
