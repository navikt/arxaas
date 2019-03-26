package no.oslomet.aaas.service;
import no.oslomet.aaas.controller.AnonymizationController;
import no.oslomet.aaas.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerAnonymizationService {

    private Logger anonymizationLogger = LoggerFactory.getLogger(AnonymizationController.class);

    public void loggAnonymizationPayload(Request payload){
        anonymizationLogger.info("Request received: "+" Size of data set: "+"Number of rows = "+ numRows(payload) +", Number of columns "+ numColumns(payload));
    }


    private int numColumns(Request payload){
        if(payload == null || payload.getData() == null) return 0;
        return  payload.getData().get(0).length;
    }

    private int numRows(Request payload){
        if(payload == null || payload.getData() == null) return 0;
        return payload.getData().size();
    }
}
