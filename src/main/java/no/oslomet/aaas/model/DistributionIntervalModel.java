package no.oslomet.aaas.model;

public class DistributionIntervalModel {
    private final String interval;
    private final double recordsWithRiskWithinInteval;
    private final double recordsWithMaxmalRiskWithinInterval;

    public DistributionIntervalModel(String interval,double recordsWithRiskWithinInteval,double recordsWithMaxmalRiskWithinInterval){
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
