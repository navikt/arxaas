package no.oslomet.aaas.model;

import org.deidentifier.arx.ARXLattice;
import org.deidentifier.arx.ARXResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonymizationMetrics {

    private List<AttributeGeneralizationRow> attributeGeneralization = new ArrayList<AttributeGeneralizationRow>();

    public AnonymizationMetrics(ARXResult result){
        ARXLattice.ARXNode node = result.getOutput().getTransformation();
        for(String attribute : node.getQuasiIdentifyingAttributes()) {
            attributeGeneralization.add(new AttributeGeneralizationRow(result.getOutput().getDefinition().getAttributeType(attribute).toString(), node.getGeneralization(attribute)));
        }
    }

    public List<AttributeGeneralizationRow> getAttributeRows() {
        return attributeGeneralization;
    }
}
