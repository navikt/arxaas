package no.oslomet.aaas.service;

import no.oslomet.aaas.hierarchies.ARXHierarchy;
import no.oslomet.aaas.hierarchies.HierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.HierarchyRequest;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HierarchyService {

    public HierarchyService(){ }


    private HierarchyBuilder hierarchyBuilder(HierarchyRequest hierarchyRequest){
        Objects.requireNonNull(hierarchyRequest);
        Objects.requireNonNull(hierarchyRequest.getBuilder());
        var builderType = hierarchyRequest.getBuilder();

        switch (builderType){
            case REDUCTIONBASED: return ARXHierarchy::reductionBased;
        }
        throw new NotImplementedException("Hierarchy generator=" + builderType.toString() + "could not be found");
    }


    public ARXHierarchy hierarchy(HierarchyRequest hierarchyRequest) {
        HierarchyBuilder builder = hierarchyBuilder(hierarchyRequest);
        return builder.hierarchy(hierarchyRequest);
    }
}
