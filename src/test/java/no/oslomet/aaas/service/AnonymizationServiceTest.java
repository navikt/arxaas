package no.oslomet.aaas.service;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.anonymizer.ARXAnonymiser;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.utils.ARXConfigurationSetter;
import no.oslomet.aaas.utils.ARXModelSetter;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * [TODO] MOCK Anonymizer and Analyser dependencies
 */

public class AnonymizationServiceTest {

    private AnonymizationService anonymizationService;
    private AnonymizationPayload testPayload;
    private MetaData testMetaData;
    private ARXWrapper testARXWrapper;

    @Before
    public void setUp() {
        testARXWrapper = new ARXWrapper(new ARXConfigurationSetter(), new ARXModelSetter());
        anonymizationService = new AnonymizationService(new ARXAnonymiser(testARXWrapper),
                new ARXAnalyser(testARXWrapper, new ARXModelSetter(), new ARXPayloadAnalyser()));
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
    public void anonymize_result() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testPayload);
        List<String[]> actual= test.getAnonymizeResult().getData();
        String[][] rawData = {{"age", "gender", "zipcode" },
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"},
                {"*", "female", "816**"},
                {"*", "male", "816**"}};
        List<String[]> expected = List.of(rawData);
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    public void anonymize_beforeAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testPayload);
        String actual = String.valueOf(test.getBeforeAnonymizationMetrics());
        String expected = "{measure_value=[%], " +
                "records_affected_by_highest_risk=100.0, " +
                "sample_uniques=100.0, estimated_prosecutor_risk=100.0, " +
                "population_model=ZAYATZ, " +
                "records_affected_by_lowest_risk=100.0, " +
                "estimated_marketer_risk=100.0, " +
                "highest_prosecutor_risk=100.0, " +
                "estimated_journalist_risk=100.0, " +
                "lowest_risk=100.0, " +
                "average_prosecutor_risk=100.0, " +
                "population_uniques=100.0, " +
                "quasi_identifiers=[zipcode, gender]}";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void anonymize_afterAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testPayload);
        String actual = String.valueOf(test.getAfterAnonymizationMetrics());
        String expected = "{measure_value=[%], " +
                "records_affected_by_highest_risk=45.45454545454545, " +
                "sample_uniques=0.0, estimated_prosecutor_risk=20.0, " +
                "population_model=DANKAR, " +
                "records_affected_by_lowest_risk=54.54545454545454, " +
                "estimated_marketer_risk=18.181818181818183, " +
                "highest_prosecutor_risk=20.0, " +
                "estimated_journalist_risk=20.0, " +
                "lowest_risk=16.666666666666664, " +
                "average_prosecutor_risk=18.181818181818183, " +
                "population_uniques=0.0, " +
                "quasi_identifiers=[zipcode, gender]}";
        Assert.assertEquals(expected,actual);
    }
}