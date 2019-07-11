package no.nav.arxaas.analyzer;

import no.nav.arxaas.model.risk.ReIdentificationRisk;
import no.nav.arxaas.model.risk.RiskProfile;
import no.nav.arxaas.model.risk.DistributionOfRisk;
import no.nav.arxaas.model.Request;
import no.nav.arxaas.utils.DataFactory;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Analyzer class using the ARX library to implement the analyzation
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
        return ReIdentificationRisk.create(dataToAnalyse,pModel);
    }

}
