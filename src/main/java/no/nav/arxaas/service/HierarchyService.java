package no.nav.arxaas.service;

import no.nav.arxaas.hierarchy.Hierarchy;
import no.nav.arxaas.hierarchy.HierarchyBuilder;
import no.nav.arxaas.hierarchy.HierarchyRequest;
import org.springframework.stereotype.Service;


@Service
public class HierarchyService {

    public HierarchyService(){ }

    public Hierarchy hierarchy(HierarchyRequest hierarchyRequest) {
        HierarchyBuilder builder = hierarchyRequest.getBuilder();
        return builder.build(hierarchyRequest.getColumn());
    }
}
