package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RedactionBasedHierarchyBuilder.class, name = "redactionBased"),
        @JsonSubTypes.Type(value = IntervalBasedHierarchyBuilder.class, name = "intervalBased")
})
public interface HierarchyBuilder {

    Hierarchy build(String[] column);
}
