package no.nav.arxaas.model.risk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.deidentifier.arx.risk.RiskEstimateBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DistributionOfRisk {


    static final String[] interval = {"[0,1e-6)","[1e-6,1e-5)","[1e-5,0.0001)","[0.0001,0.001)","[0.001,0.01)","[0.01,0.1)"
            ,"[0.1,1)","[1,2)","[2,3)","[3,4)","[4,5)","[5,6)","[6,7)","[7,8)","[8,9)","[9,10)","[10,12.5)"
            ,"[12.5,14.3)","[14.3,16.7)","[16.7,20)","[20,25)","[25,33.4)","[33.4,50)","[50,100]"};

    private final List<RiskInterval> riskIntervalList;

    @JsonCreator
    private DistributionOfRisk(List<RiskInterval> riskIntervalList){
        this.riskIntervalList = riskIntervalList;
    }

    @JsonGetter
    public List<RiskInterval> getRiskIntervalList(){
        return this.riskIntervalList;
    }

    static DistributionOfRisk createFromRiskAndMaxRisk(double[] recordsOfRiskWithinInterval, double[] recordsOfMaximalRiskWithinInterval){
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


    public static DistributionOfRisk create(RiskEstimateBuilder riskEstimateBuilder){
        return createFromRiskAndMaxRisk(distributionOfRecordsWithRisk(riskEstimateBuilder),
                distributionOfRecordsWithMaximalRisk(riskEstimateBuilder));
    }
    /***
     * Returns a double[] that contains Risk records on the different prosecutor risk ranges.
     * @param riskEstimateBuilder for a dataset
     * @return double[] that contains Risk records on the different prosecutor risk ranges
     */
    private static double[] distributionOfRecordsWithRisk(RiskEstimateBuilder riskEstimateBuilder){
        return riskEstimateBuilder
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsForRiskThresholds();
    }

    /***
     * Returns a double[] that contains maximal risk records on the different prosecutor risk ranges.
     * @param riskEstimateBuilder for a dataset
     * @return double[] that contains maximal risk records on the different prosecutor risk ranges
     */
    private static double[] distributionOfRecordsWithMaximalRisk(RiskEstimateBuilder riskEstimateBuilder){
        return riskEstimateBuilder
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsForCumulativeRiskThresholds();
    }

    @Override
    public String toString() {
        return "DistributionOfRisk{" +
                "riskIntervalList=" + riskIntervalList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionOfRisk that = (DistributionOfRisk) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(riskIntervalList);
    }

    public static class RiskInterval {
        private final String interval;
        private final double recordsWithRiskWithinInterval;
        private final double recordsWithMaximalRiskWithinInterval;

        RiskInterval(String interval, double recordsWithRiskWithinInterval, double recordsWithMaximalRiskWithinInterval){
            this.interval = interval;
            this.recordsWithRiskWithinInterval = recordsWithRiskWithinInterval;
            this.recordsWithMaximalRiskWithinInterval = recordsWithMaximalRiskWithinInterval;
        }

        public String getInterval() {
            return interval;
        }

        public double getRecordsWithRiskWithinInterval() {
            return recordsWithRiskWithinInterval;
        }

        public double getrecordsWithMaximalRiskWithinInterval() {
            return recordsWithMaximalRiskWithinInterval;
        }

        @Override
        public String toString() {
            return "RiskInterval{" +
                    "interval='" + interval + '\'' +
                    ", recordsWithRiskWithinInterval=" + recordsWithRiskWithinInterval +
                    ", recordsWithMaximalRiskWithinInterval=" + recordsWithMaximalRiskWithinInterval +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RiskInterval that = (RiskInterval) o;
            return this.hashCode() == that.hashCode();
        }

        @Override
        public int hashCode() {
            return Objects.hash(interval, recordsWithRiskWithinInterval, recordsWithMaximalRiskWithinInterval);
        }
    }
}
