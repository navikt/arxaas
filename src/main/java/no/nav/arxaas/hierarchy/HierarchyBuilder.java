package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.nav.arxaas.hierarchy.interval.IntervalBasedHierarchyBuilder;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RedactionBasedHierarchyBuilder.class, name = "redactionBased"),
        @JsonSubTypes.Type(value = IntervalBasedHierarchyBuilder.class, name = "intervalBased"),
        @JsonSubTypes.Type(value = OrderBasedHierarchyBuilder.class, name = "orderBased"),
        @JsonSubTypes.Type(value = DateBasedHierarchyBuilder.class, name = "dateBased")
})
public interface HierarchyBuilder {

    Hierarchy build(String[] column);
}
