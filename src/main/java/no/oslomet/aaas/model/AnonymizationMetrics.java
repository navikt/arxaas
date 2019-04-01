package no.oslomet.aaas.model;

import org.deidentifier.arx.ARXLattice;
import org.deidentifier.arx.ARXResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Model class for displaying metrics from the anonymization process
 */
public class AnonymizationMetrics {

    private List<AttributeGeneralizationRow> attributeGeneralization;
    private Long processTimeMillisecounds;
    private Set privacyModels;

    /**
     * Constructor for populating the class with data from a {@link ARXResult} object
     * @param result Incoming {@link ARXResult} containing the data from a completed anonymizaton
     */
    public AnonymizationMetrics(ARXResult result) {
        attributeGeneralization = gatherGeneralizationAttributes(result);
        processTimeMillisecounds = gatherProcessTime(result);
        privacyModels = gatherPrivacyModels(result);
    }

    /**
     * Gathers the name, types and generalization level for each attribute and returns them in the form of a {@link AttributeGeneralizationRow}
     * @param result source where the data is gathered from
     * @return List of {@link AttributeGeneralizationRow}'s
     */
    static public List<AttributeGeneralizationRow> gatherGeneralizationAttributes(ARXResult result) {
        List<AttributeGeneralizationRow> attributeGeneralizationList = new ArrayList<AttributeGeneralizationRow>();
        ARXLattice.ARXNode node = result.getOutput().getTransformation();
        for (String attribute : node.getQuasiIdentifyingAttributes()) {
            attributeGeneralizationList.add(new AttributeGeneralizationRow(attribute, result.getOutput().getDefinition().getAttributeType(attribute).toString(), node.getGeneralization(attribute)));
        }
        return attributeGeneralizationList;
    }

    /**
     * Gathers the elapsed time the anonymization process took in milliseconds
     * @param result Source {@link ARXResult} which the data is gathered from
     * @return Time the anonymization process have taken in milliseconds
     */
    static public long gatherProcessTime(ARXResult result) {
        return result.getTime();
    }

    /**
     * Gathers set of privacymodel data from result object
     * @param result Source {@link ARXResult} which the data is gathered from
     * @return Set with data from the privacy model settings currently being applied to the anonymization process
     */
    static public Set gatherPrivacyModels(ARXResult result) {
        return result.getConfiguration().getPrivacyModels();
    }

    public List<AttributeGeneralizationRow> getAttributeGeneralization() {
        return attributeGeneralization;
    }

    public Long getProcessTimeMillisecounds() {
        return processTimeMillisecounds;
    }

    public Set getPrivacyModels() {
        return privacyModels;
    }
}
