package no.oslomet.aaas.analyser;

import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.service.AnalysationService;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;
import static org.junit.Assert.*;

public class ARXAnalyserTest {

    private Analyser testAnalyser;
    private AnalysationPayload testPayload;

    @Before
    public void setUp() {
        testAnalyser = new ARXAnalyser(new ARXWrapper(), new ARXPayloadAnalyser());

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
        Map<String, SensitivityModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);

        testPayload = new AnalysationPayload(testData,testMapAttribute);
    }

    @Test
    public void analyse__run() {
        var result = testAnalyser.analyse(testPayload);
    }
}