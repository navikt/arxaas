package no.oslomet.aaas.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class DistributionOfRiskTest {

    private String [] interval = DistributionOfRisk.interval;

    @Test
    void createFromRiskAndMaxRisk_from_Payload() {

        double[] recordsOfRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
        double[] recordsOfMaximalRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};

        DistributionOfRisk distributionOfRisk = DistributionOfRisk.createFromRiskAndMaxRisk(recordsOfRiskWithInInterval,recordsOfMaximalRiskWithInInterval);
        var distributionIntervalModelList = distributionOfRisk.getRiskIntervalList();

        int index = distributionIntervalModelList.size()-1;
        for( DistributionOfRisk.RiskInterval actual: distributionIntervalModelList){
            Assertions.assertEquals(interval[index], actual.getInterval());
            Assertions.assertEquals(recordsOfRiskWithInInterval[index],actual.getRecordsWithRiskWithinInteval());
            Assertions.assertEquals(recordsOfMaximalRiskWithInInterval[index],actual.getRecordsWithMaxmalRiskWithinInterval());
            index--;
        }
    }

    @Test
    void createFromRiskAndMaxRisk_from_result() {
        double[] recordsOfRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,0.45454545454545453,0.0,0.0,0.0,0.0};
        double[] recordsOfMaximalRiskWithInInterval ={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5454545454545454,1.0,1.0,1.0,1.0,1.0};


        DistributionOfRisk distributionOfRisk = DistributionOfRisk.createFromRiskAndMaxRisk(recordsOfRiskWithInInterval,recordsOfMaximalRiskWithInInterval);
        var distributionIntervalModelList = distributionOfRisk.getRiskIntervalList();

        int index = distributionIntervalModelList.size()-1;
        for( DistributionOfRisk.RiskInterval actual: distributionIntervalModelList){
            Assertions.assertEquals(interval[index], actual.getInterval());
            Assertions.assertEquals(recordsOfRiskWithInInterval[index],actual.getRecordsWithRiskWithinInteval());
            Assertions.assertEquals(recordsOfMaximalRiskWithInInterval[index],actual.getRecordsWithMaxmalRiskWithinInterval());
            index--;
        }
    }
}