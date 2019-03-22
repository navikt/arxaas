package no.oslomet.aaas.model;

public class RiskInterval {
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