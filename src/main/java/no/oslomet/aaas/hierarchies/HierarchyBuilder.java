package no.oslomet.aaas.hierarchies;

import no.oslomet.aaas.model.hierarchy.HierarchyRequest;

public interface HierarchyBuilder {

    ARXHierarchy hierarchy(HierarchyRequest request);
}
