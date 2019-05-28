package no.oslomet.aaas.service;

import no.oslomet.aaas.hierarchy.Hierarchy;
import no.oslomet.aaas.hierarchy.HierarchyBuilder;
import no.oslomet.aaas.hierarchy.HierarchyRequest;
import org.springframework.stereotype.Service;


@Service
public class HierarchyService {

    public HierarchyService(){ }

    public Hierarchy hierarchy(HierarchyRequest hierarchyRequest) {
        HierarchyBuilder builder = hierarchyRequest.getBuilder();
        return builder.build(hierarchyRequest.getColumn());
    }
}
