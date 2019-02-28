package no.oslomet.aaas.service;

import no.oslomet.aaas.analyser.ARXAnalyser;
import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.utils.ARXConfigurationSetter;
import no.oslomet.aaas.utils.ARXModelSetter;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.IDENTIFYING;
import static no.oslomet.aaas.model.AttributeTypeModel.QUASIIDENTIFYING;

public class AnalysationServiceTest {

    private AnalysationService analysationService;
    private ARXWrapper arxWrapper = new ARXWrapper(new ARXConfigurationSetter(), new ARXModelSetter());
    private ARXModelSetter arxModelSetter = new ARXModelSetter();
    private ARXPayloadAnalyser arxPayloadAnalyser = new ARXPayloadAnalyser();
    private ARXAnalyser arxAnalyser = new ARXAnalyser(arxWrapper, arxModelSetter, arxPayloadAnalyser);

    @Before
    public void initialize(){ analysationService = new AnalysationService(arxAnalyser); }

    //-------------------------preparing test payload----------------------------//
    private AnalysationPayload testPayload;
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

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);

        testPayload = new AnalysationPayload(testData,testMapAttribute);
    }
    //------------------------------------------------------------------------//

    @Test
    public void getPayloadAnalysis() {
        String actual = String.valueOf(analysationService.analyse(testPayload).getMetrics());
        String expected ="{measure_value=[%], " +
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

}