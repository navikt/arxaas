package no.nav.arxaas.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;

import javax.validation.constraints.NotNull;

public class RedactionBasedHierarchyBuilder implements HierarchyBuilder{

    public enum Order {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    @NotNull
    private final Character paddingCharacter;
    @NotNull
    private final Character redactionCharacter;
    @NotNull
    private final Order paddingOrder;
    @NotNull
    private final Order redactionOrder;

    @JsonCreator
    public RedactionBasedHierarchyBuilder(Character paddingCharacter, Character redactionCharacter, Order paddingOrder, Order redactionOrder) {
        this.paddingCharacter = paddingCharacter;
        this.redactionCharacter = redactionCharacter;
        this.paddingOrder = paddingOrder;
        this.redactionOrder = redactionOrder;
    }

    @Override
    public Hierarchy build(String[] column) {

        var arxRedactionOrder = HierarchyBuilderRedactionBased.Order.valueOf(redactionOrder.toString());
        var arxPaddingOrder = HierarchyBuilderRedactionBased.Order.valueOf(paddingOrder.toString());

        HierarchyBuilderRedactionBased<?> builder
                = HierarchyBuilderRedactionBased.create(
                arxPaddingOrder,
                arxRedactionOrder,
                paddingCharacter,
                redactionCharacter);
        builder.prepare(column);

        return new Hierarchy(builder.build().getHierarchy());
    }

    public Character getPaddingCharacter() {
        return paddingCharacter;
    }

    public Character getRedactionCharacter() {
        return redactionCharacter;
    }

    public Order getPaddingOrder() {
        return paddingOrder;
    }

    public Order getRedactionOrder() {
        return redactionOrder;
    }
}
