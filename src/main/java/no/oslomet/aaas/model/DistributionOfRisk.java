package no.oslomet.aaas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;

public class DistributionOfRisk {


    static final String[] interval = {"]0,1e-6]","]1e-6,1e-5]","]1e-5,0.0001]","]0.0001,0.001]","]0.001,0.01]","]0.01,0.1]"
            ,"]0.1,1]","]1,2]","]2,3]","]3,4]","]4,5]","]5,6]","]6,7]","]7,8]","]8,9]","]9,10]","]10,12.5]"
            ,"]12.5,14.3]","]14.3,16.7]","]16.7,20]","]20,25]","]25,33.4]","]33.4,50]","]50,100]"};

    private final List<RiskInterval> riskIntervalList;

    @JsonCreator
    private DistributionOfRisk(List<RiskInterval> riskIntervalList){
        this.riskIntervalList = riskIntervalList;
    }

    @JsonGetter
    public List<RiskInterval> getRiskIntervalList(){
        return this.riskIntervalList;
    }

    public static DistributionOfRisk createFromRiskAndMaxRisk(double[] recordsOfRiskWithinInterval, double[] recordsOfMaximalRiskWithinInterval){
        List<RiskInterval> listOfDistributionOfRiskInterval = new ArrayList<>();
        for(int x = recordsOfRiskWithinInterval.length-1;x>=0;x--){
            RiskInterval riskInterval = new RiskInterval(
                    interval[x],
                    recordsOfRiskWithinInterval[x],
                    recordsOfMaximalRiskWithinInterval[x]);
            listOfDistributionOfRiskInterval.add(riskInterval);
        }
        return new DistributionOfRisk(listOfDistributionOfRiskInterval);
    }

    public static class RiskInterval {
        private final String interval;
        private final double recordsWithRiskWithinInteval;
        private final double recordsWithMaxmalRiskWithinInterval;

        RiskInterval(String interval, double recordsWithRiskWithinInteval, double recordsWithMaxmalRiskWithinInterval){
            this.interval = interval;
            this.recordsWithRiskWithinInteval = recordsWithRiskWithinInteval;
            this.recordsWithMaxmalRiskWithinInterval = recordsWithMaxmalRiskWithinInterval;
        }

        public String getInterval() {
            return interval;
        }

        public double getRecordsWithRiskWithinInteval() {
            return recordsWithRiskWithinInteval;
        }

        public double getRecordsWithMaxmalRiskWithinInterval() {
            return recordsWithMaxmalRiskWithinInterval;
        }
    }
}
