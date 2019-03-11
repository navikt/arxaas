package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.utils.ARXConfigurationSetter;
import no.oslomet.aaas.utils.ARXModelSetter;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Before;
import org.junit.Test;

public class ARXAnonymiserTest {

    private Anonymiser testAnonymiser;
    private AnonymizationPayload testPayload;


    @Before
    public void setUp() {
        testAnonymiser = new ARXAnonymiser(new ARXWrapper(new ARXConfigurationSetter(), new ARXModelSetter()));
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
    public void anonymize__run() {
        var result = testAnonymiser.anonymize(testPayload);
    }
}