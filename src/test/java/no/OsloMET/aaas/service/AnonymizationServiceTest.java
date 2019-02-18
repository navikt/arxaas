package no.OsloMET.aaas.service;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.service.AnonymizationService;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXResponseAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;

public class AnonymizationServiceTest {

    private AnonymizationService anonymizationService;

    private ARXWrapper arxWrapper = new ARXWrapper();
    private ARXPayloadAnalyser arxPayloadAnalyser = new ARXPayloadAnalyser();
    private ARXResponseAnalyser arxResponseAnalyser = new ARXResponseAnalyser();

    @Before
    public void initialize(){ anonymizationService = new AnonymizationService(arxWrapper,arxPayloadAnalyser,arxResponseAnalyser); }

    //-------------------------preparing test payload----------------------------//
    private AnonymizationPayload testPayload = new AnonymizationPayload();
    private MetaData testMetaData = new MetaData();

    @Before
    public void generateTestData() {
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

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, SensitivityModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setSensitivityList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String ,String[][]> testMapHierarchy = new HashMap<>();
        String [][] testHeirarchy = new String[][]{
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81668", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81669", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81670", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81671", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81672", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81673", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81674", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81675", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81676", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode",testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel,Map<String,String>> testMapPrivacy = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMapPrivacy.put(KANONYMITY,testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
    }
    //------------------------------------------------------------------------//

    @Test
    public void anonymize() throws IOException {
        String actual = anonymizationService.anonymize(testPayload);
        String expected = "age;gender;zipcode\n" +
                "*;male;816**\n" +
                "*;female;816**\n" +
                "*;male;816**\n" +
                "*;female;816**\n" +
                "*;male;816**\n" +
                "*;female;816**\n" +
                "*;male;816**\n" +
                "*;female;816**\n" +
                "*;male;816**\n" +
                "*;female;816**\n" +
                "*;male;816**\n";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getPayloadAnalysis() {
        String actual = anonymizationService.getPayloadAnalysis(testPayload);
        String expected = "Measure: Value;[%]\n" +
                "Lowest risk;100.0%\n" +
                "Records affected by lowest risk;100.0%\n" +
                "Average prosecutor risk;100.0%\n" +
                "Highest prosecutor risk;100.0%\n" +
                "Record affected by highest risk;100.0%\n" +
                "Estimated prosecutor risk;100.0%\n" +
                "Estimated prosecutor risk;100.0%\n" +
                "Estimated journalist risk;100.0%\n" +
                "Estimated marketer risk;100.0%\n" +
                "Sample uniques: 100.0%\n" +
                "Population uniques: 100.0%\n" +
                "Population model: ZAYATZ\n" +
                "Quasi-identifiers: [zipcode, gender]\n";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getResponseAnalysis() throws IOException {
        String actual = anonymizationService.getResponseAnalysis(testPayload);
        String expected = "Measure: Value;[%]\n" +
                "Lowest risk;16.666666666666664%\n" +
                "Records affected by lowest risk;54.54545454545454%\n" +
                "Average prosecutor risk;18.181818181818183%\n" +
                "Highest prosecutor risk;20.0%\n" +
                "Record affected by highest risk;45.45454545454545%\n" +
                "Estimated prosecutor risk;20.0%\n" +
                "Estimated prosecutor risk;20.0%\n" +
                "Estimated journalist risk;20.0%\n" +
                "Estimated marketer risk;18.181818181818183%\n" +
                "Sample uniques: 0.0%\n" +
                "Population uniques: 0.0%\n" +
                "Population model: DANKAR\n" +
                "Quasi-identifiers: [zipcode, gender]\n";
        Assert.assertEquals(expected,actual);
    }
}