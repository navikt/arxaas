package no.oslomet.aaas.anonymizer;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.exception.UnableToAnonymizeDataException;
import no.oslomet.aaas.model.anonymity.AnonymizeResult;
import no.oslomet.aaas.model.Attribute;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


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