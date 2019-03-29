package no.oslomet.aaas.service;

import no.oslomet.aaas.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    public void loggPayload(Request payload, String IP, Class classToLogg) {
        Logger logger = LoggerFactory.getLogger(classToLogg);
        logger.info("Request received, " + "Size of data set: " + "Number of rows = " + numRows(payload) + ", Number of columns " + numColumns(payload) + ", Bytesize = " + bytesize(payload) +", Request Source IP = " + IP);
    }

    private int numColumns(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().get(0).length;
    }

    private int numRows(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().size();
    }

    private int bytesize(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().toString().length();
    }
}
