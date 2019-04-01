package no.oslomet.aaas.model;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.anonymizer.ARXAnonymizer;
import no.oslomet.aaas.utils.ARXConfigurationFactory;
import no.oslomet.aaas.utils.ARXDataFactory;
import no.oslomet.aaas.utils.ARXPrivacyCriterionFactory;
import org.deidentifier.arx.ARXResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AnonymizationMetricsTest {

    ARXAnonymizer testAnonymizer;
    Request testRequestPayload;
    AnonymizeResult result;


    @BeforeEach
    void setUp() {
        testAnonymizer = new ARXAnonymizer(new ARXDataFactory(), new ARXConfigurationFactory(new ARXPrivacyCriterionFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
        result = testAnonymizer.anonymize(testRequestPayload);
        System.out.println();
    }

    @Test
    void gatherGeneralizationAttributes() {
        List<AttributeGeneralizationRow> generalizationAttributes = result.getMetrics().getAttributeGeneralization();
        assertEquals(1, generalizationAttributes.size());
        assertEquals(2, generalizationAttributes.get(0).generalizationLevel);
        assertEquals("zipcode", generalizationAttributes.get(0).name);
        assertEquals("QUASI_IDENTIFYING_ATTRIBUTE", generalizationAttributes.get(0).type);
    }

    @Test
    void gatherProcessTime() {
        assertNotNull(result.getMetrics().getProcessTimeMillisecounds());
    }

    @Test
    void gatherPrivacyModels() {
        assertEquals(2, result.getMetrics().getPrivacyModels().size());
    }
}