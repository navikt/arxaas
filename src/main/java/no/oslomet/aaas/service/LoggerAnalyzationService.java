package no.oslomet.aaas.service;
import no.oslomet.aaas.controller.AnalyzationController;
import no.oslomet.aaas.controller.AnonymizationController;
import no.oslomet.aaas.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerAnalyzationService {

    private Logger analyzationLogger = LoggerFactory.getLogger(AnalyzationController.class);

    public void loggAnalyzationPayload(Request payload){
        analyzationLogger.info("\n-Request received "+"\n-Size of data set"+"\n-Number of rows = "+payload.getData().size()+"\n-Number of columns "+payload.getData().get(0).length);
    }
}
