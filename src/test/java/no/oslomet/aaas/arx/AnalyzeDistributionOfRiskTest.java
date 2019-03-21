package no.oslomet.aaas.arx;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.ARXConfigurationFactory;
import no.oslomet.aaas.utils.ARXDataFactory;
import no.oslomet.aaas.utils.ARXPrivacyCriterionFactory;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;


class AnalyzeDistributionOfRiskTest {

    private Request testPayload;
    private ARXPopulationModel pModel;

    @BeforeEach
    void generateTestData(){ testPayload= GenerateTestData.zipcodeRequestPayload(); }

    @Test
    void distributionOfRisk(){
        testPayload = GenerateTestData.zipcodeRequestPayload3Quasi();
        ARXDataFactory dataFactory = new ARXDataFactory();
        ARXConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        Data testData = dataFactory.create(testPayload);
        ARXConfiguration config = configurationFactory.create(testPayload.getPrivacyModels());

        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = null;
        try {
            result = anonymizer.anonymize(testData,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ARXPopulationModel populationmodel = ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);

        double actual = testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtCumulativeRisk(0.5d);
        Assertions.assertEquals(0.0,actual);

        assert result != null;
        double actualResult = result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtCumulativeRisk(0.5d);
        Assertions.assertEquals(1.0,actualResult);
/*
        // Perform risk analysis
        System.out.println("- Input data");
        System.out.print("\n- Records at 50% risk: " + testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtRisk(0.5d));
        System.out.println("\n- Records at <=50% risk: " + testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtCumulativeRisk(0.5d));

        // Perform risk analysis
        System.out.println("\n- Output data");
        assert result != null;
        System.out.print("\n- Records at 50% risk: " + result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtRisk(0.5d));
        System.out.print("\n- Records at <=50% risk: " + result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsAtCumulativeRisk(0.5d));
   */
    }

    @Test
    void distributionOfRiskWithThreshold(){

        testPayload = GenerateTestData.zipcodeRequestPayload3Quasi();
        ARXDataFactory dataFactory = new ARXDataFactory();
        ARXConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        Data testData = dataFactory.create(testPayload);
        ARXConfiguration config = configurationFactory.create(testPayload.getPrivacyModels());

        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXResult result = null;
        try {
            result = anonymizer.anonymize(testData,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ARXPopulationModel populationmodel = ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);

        testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForRiskThresholds();
        assert result != null;
        result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForRiskThresholds();

       /* // Perform risk analysis
        System.out.println("- Input data");
        System.out.print("\n- Records with risk: " + Arrays.toString(testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForRiskThresholds()));
        System.out.println("\n- Records with maximal risk: " + Arrays.toString(testData.getHandle().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForCumulativeRiskThresholds()));

        // Perform risk analysis
        System.out.println("\n- Output data");
        assert result != null;
        System.out.print("\n- Records with risk: " + Arrays.toString(result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForRiskThresholds()));
        System.out.print("\n- Records with maximal risk: " + Arrays.toString(result.getOutput().getRiskEstimator(populationmodel).getSampleBasedRiskDistribution().getFractionOfRecordsForCumulativeRiskThresholds()));
   */ }


    @Test
    void listOfRisk(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data testData = dataFactory.create(testPayload);
        pModel= ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);
        int[] test = testData.getHandle().getRiskEstimator(pModel).getEquivalenceClassModel().getHistogram();

        //System.out.println(Arrays.toString(test));
    }

    @Test
    void listOfRisk_numClasses(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data testData = dataFactory.create(testPayload);
        pModel= ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);
        double test = testData.getHandle().getRiskEstimator(pModel).getEquivalenceClassModel().getNumClasses();

       // System.out.println(test);
    }

    @Test
    void listOfRisk_numRecords(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data testData = dataFactory.create(testPayload);
        pModel= ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);
        double test = testData.getHandle().getRiskEstimator(pModel).getEquivalenceClassModel().getNumRecords();

        //System.out.println(test);
    }

    @Test
    void test_ResultAnalysis(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        ARXConfigurationFactory configurationFactory = new ARXConfigurationFactory(new ARXPrivacyCriterionFactory());
        Data testData = dataFactory.create(testPayload);
        ARXConfiguration config = configurationFactory.create(testPayload.getPrivacyModels());

        ARXAnonymizer anonymizer = new ARXAnonymizer();
        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        ARXResult result = null;
        try {
            result = anonymizer.anonymize(testData,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert result != null;
        ARXLattice.ARXNode nodeTop = result.getLattice().getTop();
        ARXLattice.ARXNode nodeButtom = result.getLattice().getBottom();

        DataHandle optimal = result.getOutput();
        DataHandle top = result.getOutput(nodeTop);
        DataHandle bottom = result.getOutput(nodeButtom);
/*
        System.out.println("optimal");
        optimal.iterator().forEachRemaining(strings -> System.out.println(Arrays.toString(strings)));
        System.out.println("top");
        top.iterator().forEachRemaining(strings -> System.out.println(Arrays.toString(strings)));
        System.out.println("bottom");
        bottom.iterator().forEachRemaining(strings -> System.out.println(Arrays.toString(strings)));*/
    }
}
