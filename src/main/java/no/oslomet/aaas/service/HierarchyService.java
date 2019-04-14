package no.oslomet.aaas.service;

import no.oslomet.aaas.model.hierarchy.Hierarchy;
import no.oslomet.aaas.model.hierarchy.HierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.HierarchyRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HierarchyService {

    public HierarchyService(){ }

    public Hierarchy hierarchy(HierarchyRequest hierarchyRequest) {
        HierarchyBuilder builder = hierarchyRequest.getBuilder();
        return builder.build(hierarchyRequest.getColumn());
    }
}
