package no.oslomet.aaas.model.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderOrderBased;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderBasedHierarchyBuilder implements HierarchyBuilder {

    @NotNull
    private final List<Level> levels;

    @JsonCreator
    public OrderBasedHierarchyBuilder(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public Hierarchy build(String[] column) {

        HierarchyBuilderOrderBased<String> builder
                = HierarchyBuilderOrderBased.create(DataType.ORDERED_STRING, false);
        for (Level level :
                levels) {
            level.applyTo(builder);
        }
        builder.prepare(column);
        return new Hierarchy(builder.build().getHierarchy());
    }

    public List<Level> getLevels() {
        return levels;
    }
}
