package no.oslomet.aaas.service;

import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.anonymizer.ARXAnonymiser;
import no.oslomet.aaas.model.*;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;
import static org.junit.Assert.*;

/**
 * [TODO] MOCK Anonymizer and Analyser dependencies
 */

public class AnonymizationServiceTest {

    AnonymizationService anonymizationService;
    AnonymizationPayload testPayload;
    MetaData testMetaData;
    ARXWrapper testARXWrapper;

    @Before
    public void setUp() {
        testARXWrapper = new ARXWrapper();
        anonymizationService = new AnonymizationService(new ARXAnonymiser(testARXWrapper),
                new ARXAnalyser(testARXWrapper, new ARXPayloadAnalyser()));
        testPayload = new AnonymizationPayload();
        generateTestData();
    }

    protected void generateTestData() {
        String testData ="age, gender, zipcode\n" +
                "34, male, 81667\n" +
                "35, female, 81668\n" +
                "36, male, 81669\n" +
                "37, female, 81670\n" +
                "38, male, 81671\n" +
                "39, female, 81672\n" +
                "40, male, 81673\n" +
                "41, female, 81674\n" +
                "42, male, 81675\n" +
                "43, female , 81676\n" +
                "44, male, 81677";

        testPayload.setData(testData);

        testMetaData = new MetaData();

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, SensitivityModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age", IDENTIFYING);
        testMapAttribute.put("gender", QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setSensitivityList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String, String[][]> testMapHierarchy = new HashMap<>();
        String[][] testHeirarchy = new String[][]{
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                , {"81668", "8166*", "816**", "81***", "8****", "*****"}
                , {"81669", "8166*", "816**", "81***", "8****", "*****"}
                , {"81670", "8167*", "816**", "81***", "8****", "*****"}
                , {"81671", "8167*", "816**", "81***", "8****", "*****"}
                , {"81672", "8167*", "816**", "81***", "8****", "*****"}
                , {"81673", "8167*", "816**", "81***", "8****", "*****"}
                , {"81674", "8167*", "816**", "81***", "8****", "*****"}
                , {"81675", "8167*", "816**", "81***", "8****", "*****"}
                , {"81676", "8167*", "816**", "81***", "8****", "*****"}
                , {"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode", testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel, Map<String, String>> testMapPrivacy = new HashMap<>();
        Map<String, String> testMapValue = new HashMap<>();
        testMapValue.put("k", "5");
        testMapPrivacy.put(KANONYMITY, testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
    }

    @Test
    public void anonymize_result() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testPayload);
        String actual = String.valueOf(test.getAnonymizeResult().getData());
        String expected = "age,gender,zipcode\n" +
                "*,male,816**\n" +
                "*,female,816**\n" +
                "*,male,816**\n" +
                "*,female,816**\n" +
                "*,male,816**\n" +
                "*,female,816**\n" +
                "*,male,816**\n" +
                "*,female,816**\n" +
                "*,male,816**\n" +
                "*,female,816**\n" +
                "*,male,816**\n";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void anonymize_beforeAnonymizationMetrics() {
        AnonymizationResultPayload test= anonymizationService.anonymize(testPayload);
        String actual = String.valueOf(test.getBeforeAnonymizationMetrics());
        String expected = "{measure_value=[%], " +
                "record_affected_by_highest_risk=100.0, " +
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
                "record_affected_by_highest_risk=45.45454545454545, " +
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