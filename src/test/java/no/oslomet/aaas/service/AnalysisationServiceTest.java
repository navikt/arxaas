package no.oslomet.aaas.service;


import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.utils.ARXPayloadAnalyser;
import no.oslomet.aaas.utils.ARXResponseAnalyser;
import no.oslomet.aaas.utils.ARXWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;
import static no.oslomet.aaas.model.SensitivityModel.QUASIIDENTIFYING;
import static org.junit.Assert.*;



public class AnalysisationServiceTest {

    AnalysisationService analysisationService;
    HashMap<String, SensitivityModel> testAttributes;
    String testData;

    @Before
    public void setUp() throws Exception {

        this.analysisationService = new AnalysisationService(new ARXWrapper(),
                new ARXPayloadAnalyser(),
                new ARXResponseAnalyser());

        testAttributes  = new HashMap<>();
        testAttributes.put("age",IDENTIFYING);
        testAttributes.put("gender",QUASIIDENTIFYING);
        testAttributes.put("zipcode",QUASIIDENTIFYING);

        testData ="age, gender, zipcode\n" +
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


    }

    @Test
    public void getPayloadAnalysis__run() {
        var result = analysisationService.getPayloadAnalysis(new AnalysationPayload(testData, testAttributes));
    }

}