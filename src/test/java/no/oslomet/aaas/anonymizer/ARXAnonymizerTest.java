package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.Before;
import org.junit.Test;

public class ARXAnonymizerTest {

    private Anonymizer testAnonymizer;
    private Request testRequestPayload;


    @Before
    public void setUp() {
        testAnonymizer = new ARXAnonymizer(new ARXDataFactory(), new ARXConfigurationFactory(new ARXPrivacyCriterionFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    public void anonymize__run() {
        var result = testAnonymizer.anonymize(testRequestPayload);
    }
}