package no.oslomet.aaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.oslomet.aaas.hierarchy.interval.IntervalBasedHierarchyBuilder;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RedactionBasedHierarchyBuilder.class, name = "redactionBased"),
        @JsonSubTypes.Type(value = IntervalBasedHierarchyBuilder.class, name = "intervalBased"),
        @JsonSubTypes.Type(value = OrderBasedHierarchyBuilder.class, name = "orderBased")
})
public interface HierarchyBuilder {

    Hierarchy build(String[] column);
}
