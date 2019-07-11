package no.nav.arxaas.model;

import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.anonymizer.ARXAnonymizer;
import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.utils.ARXConfigurationFactory;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.ARXPrivacyCriterionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AnonymizationMetricsTest {

    private ARXAnonymizer testAnonymizer;
    private Request testRequestPayload;
    private AnonymizeResult result;


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