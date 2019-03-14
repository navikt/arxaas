package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;

public class ARXAnonymiserTest {

    private Anonymiser testAnonymiser;
    private Request testRequestPayload;


    @Before
    public void setUp() {
        testAnonymiser = new ARXAnonymiser(new ARXDataFactory(), new ARXConfigurationFactory(new ARXPrivacyCriterionFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void anonymize__run() {
        var result = testAnonymiser.anonymize(testRequestPayload);
    }
}