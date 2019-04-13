package no.oslomet.aaas.hierarchies;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.oslomet.aaas.model.hierarchy.HierarchyRequest;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased.Order;

import java.util.Map;

public class ARXHierarchy {

    public String[][] hierarchy;


    @JsonCreator
    private ARXHierarchy(String[][] hierarchy){
        this.hierarchy = hierarchy;
    }


    public static ARXHierarchy reductionBased(HierarchyRequest hierarchyRequest){
        var params = hierarchyRequest.getParams();
        char paddingCharacter = params.getOrDefault("paddingCharacter", " ").charAt(0);
        char redactionCharacter = params.getOrDefault("redactionCharacter", "*").charAt(0);
        Order paddingOrder = Order.valueOf(params.getOrDefault("paddingOrder", "RIGHT_TO_LEFT"));
        Order redactionOrder = Order.valueOf(params.getOrDefault("redactionOrder", "RIGHT_TO_LEFT"));
        HierarchyBuilderRedactionBased<?> builder
                = HierarchyBuilderRedactionBased.create(
                        paddingOrder,
                        redactionOrder,
                        paddingCharacter,
                        redactionCharacter);
        builder.prepare(hierarchyRequest.getColumn());

        return new ARXHierarchy(builder.build().getHierarchy());
    }
}
