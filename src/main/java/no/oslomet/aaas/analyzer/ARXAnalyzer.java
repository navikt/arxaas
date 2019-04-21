package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.model.risk.ReIdentificationRisk;
import no.oslomet.aaas.model.risk.RiskProfile;
import no.oslomet.aaas.model.risk.DistributionOfRisk;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.utils.DataFactory;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Analyzer class using the ARX library to implement the analysation
 */
@Component
public class ARXAnalyzer implements Analyzer {

    private final DataFactory dataFactory;

    @Autowired
    public ARXAnalyzer(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    @Override
    public RiskProfile analyze(Request payload) {
        Data data = dataFactory.create(payload);
        DataHandle dataToAnalyse = data.getHandle();
        ARXPopulationModel pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);

        return new RiskProfile(reIdentificationRisk(dataToAnalyse, pModel),distributionOfRisk(dataToAnalyse,pModel));
    }

    private DistributionOfRisk distributionOfRisk(DataHandle dataToAnalyse, ARXPopulationModel pModel){
        return DistributionOfRisk.create(dataToAnalyse.getRiskEstimator(pModel));
    }

    private ReIdentificationRisk reIdentificationRisk(DataHandle dataToAnalyse, ARXPopulationModel pModel){
        return ARXReIdentificationRiskFactory.create(dataToAnalyse,pModel);
    }

}
