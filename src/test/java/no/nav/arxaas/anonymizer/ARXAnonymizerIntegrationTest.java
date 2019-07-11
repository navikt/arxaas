package no.nav.arxaas.anonymizer;

import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.exception.UnableToAnonymizeDataException;
import no.nav.arxaas.model.anonymity.AnonymizeResult;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.ARXConfigurationFactory;
import no.nav.arxaas.utils.ARXDataFactory;
import no.nav.arxaas.utils.ARXPrivacyCriterionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ARXAnonymizerIntegrationTest {

    private Anonymizer testAnonymizer;
    private Request testRequestPayload;
    private Request testRequestPayloadWithToManyQuasi;


    @BeforeEach
    void setUp() {
        testAnonymizer = new ARXAnonymizer(new ARXDataFactory(), new ARXConfigurationFactory(new ARXPrivacyCriterionFactory()));
        testRequestPayload = GenerateTestData.zipcodeRequestPayload();
        testRequestPayloadWithToManyQuasi = GenerateTestData.zipcodeRequestPayload3QuasiNoHierarchies();
    }

    @Test
    void anonymize__run() {
        AnonymizeResult result = testAnonymizer.anonymize(testRequestPayload);
    }


    @Test
    void anonymize__should_throw_exception_when_anonymization_is_impossible(){
        Assertions.assertThrows(UnableToAnonymizeDataException.class,
                () -> testAnonymizer.anonymize(testRequestPayloadWithToManyQuasi));

    }

    @Test
    void anonymize_should_return_with_list_of_attribute(){
        AnonymizeResult result = testAnonymizer.anonymize(testRequestPayload);
        Assertions.assertNotNull(result.getAttributes());
    }

    @Test
    void anonymize_should_return_with_list_of_data(){
        AnonymizeResult result = testAnonymizer.anonymize(testRequestPayload);
        Assertions.assertNotNull(result.getData());
    }

    @Test
    void anonymize_should_return_with_anonymization_status(){
        AnonymizeResult result = testAnonymizer.anonymize(testRequestPayload);
        Assertions.assertNotNull(result.getAnonymizationStatus());
    }

    @Test
    void anonymize_should_return_with_anonymization_metrics(){
        AnonymizeResult result = testAnonymizer.anonymize(testRequestPayload);
        Assertions.assertNotNull(result.getMetrics());
    }
}