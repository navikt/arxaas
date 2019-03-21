package no.oslomet.aaas.model;

import java.util.List;

public class DistributionOfRiskModel {
    private final List<DistributionIntervalModel> distributionIntervalModelList;

    public DistributionOfRiskModel(List<DistributionIntervalModel> distributionIntervalModelList){
        this.distributionIntervalModelList = distributionIntervalModelList;
    }

    public List<DistributionIntervalModel> getDistributionIntervalModelList(){
        return this.distributionIntervalModelList;
    }
}
