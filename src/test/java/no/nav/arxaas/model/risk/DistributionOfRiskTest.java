package no.nav.arxaas.model.risk;

import no.nav.arxaas.GenerateTestData;
import org.deidentifier.arx.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DistributionOfRiskTest {

    private String [] interval = DistributionOfRisk.interval;
    private double[] testRisksForThresholds;
    private double[] testMaximalRiskForThresholds;
    private Data testData;

    @BeforeEach
    void setUp(){
        testRisksForThresholds = new double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        testMaximalRiskForThresholds = new double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        testData = GenerateTestData.ageGenderZipcodeDataset();
    }

    private void assertDataIsCorrect(DistributionOfRisk distributionOfRisk, double[] risksForThresholds, double[] maxRisksForThreshold){
        var distributionIntervalModelList = distributionOfRisk.getRiskIntervalList();

        int index = distributionIntervalModelList.size()-1;
        for( DistributionOfRisk.RiskInterval actual: distributionIntervalModelList){
            Assertions.assertEquals(interval[index], actual.getInterval());
            Assertions.assertEquals(risksForThresholds[index],actual.getRecordsWithRiskWithinInterval());
            Assertions.assertEquals(maxRisksForThreshold[index],actual.getrecordsWithMaximalRiskWithinInterval());
            index--;
        }
    }

    @Test
    void createFromRiskAndMaxRisk_from_Payload() {
        DistributionOfRisk distributionOfRisk = DistributionOfRisk.createFromRiskAndMaxRisk(testRisksForThresholds,testMaximalRiskForThresholds);
       assertDataIsCorrect(distributionOfRisk, testRisksForThresholds, testMaximalRiskForThresholds);
    }

    @Test
    void createFromRiskAndMaxRisk_from_result() {
        double[] recordsOfRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,0.45454545454545453,0.0,0.0,0.0,0.0};
        double[] recordsOfMaximalRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,1.0,1.0,1.0,1.0,1.0};


        DistributionOfRisk distributionOfRisk = DistributionOfRisk.createFromRiskAndMaxRisk(recordsOfRiskWithInInterval,recordsOfMaximalRiskWithInInterval);
        assertDataIsCorrect(distributionOfRisk, recordsOfRiskWithInInterval, recordsOfMaximalRiskWithInInterval);
    }

    @Test
    void create_data_is_correct(){
        var result = DistributionOfRisk.create(testData.getHandle().getRiskEstimator());
        assertDataIsCorrect(result, testRisksForThresholds, testMaximalRiskForThresholds);
    }
}