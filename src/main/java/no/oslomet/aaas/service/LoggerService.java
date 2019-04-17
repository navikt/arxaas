package no.oslomet.aaas.service;

import no.oslomet.aaas.model.AnonymizationResultPayload;
import no.oslomet.aaas.model.PrivacyCriterionModel;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.analytics.RiskProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    public void loggPayload(Request payload, String ip, Class classToLogg) {
        Logger logger = LoggerFactory.getLogger(classToLogg);
        logger.info("Request received, " + "Size of data set: " + "Number of rows = " + numRows(payload) + ", Number of columns " + numColumns(payload) + ", Bytesize = " + bytesize(payload) + ", Request Source IP = " + ip + " Privacy models used = " + logPrivacyModel(payload));
    }

    public void loggAnalyzationResult(RiskProfile analyzationResult, Request payload, String ip, long requestProcessingTime, Class classToLogg) {
        Logger logger = LoggerFactory.getLogger(classToLogg);
        logger.info("Request complete, " + "Size of data set: " + "Number of rows = " + numRows(payload) + ", Number of columns " + numColumns(payload) + ", Bytesize = " + bytesize(payload) + ", Request Source IP = " + ip + " Request processing time = " + requestProcessingTime + " milliseconds");
    }

    public void loggAnonymizeResult(AnonymizationResultPayload payload, long requestProcessingTime, Class classToLogg, String ip) {
        Logger logger = LoggerFactory.getLogger(classToLogg);
        logger.info("Request complete, " + "Size of data set: " + "Number of rows = " + numRows(payload) + ", Number of columns " + numColumns(payload) + ", Bytesize = " + bytesize(payload) + ", Request Source IP = " + ip + " Request processing time = " + requestProcessingTime + " milliseconds");
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


    private String logPrivacyModel(Request payload) {
        if (payload == null || payload.getData() == null || payload.getPrivacyModels() == null) return "";
        String privacyModels = "";
        for (PrivacyCriterionModel privacyModel : payload.getPrivacyModels())
            privacyModels = privacyModels.concat(privacyModel.getPrivacyModel().getName()).concat(", ");
        return privacyModels;
    }


    private int numColumns(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().get(0).length;
    }

    private int numRows(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().size();
    }

    private int bytesize(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().toString().length();
    }


}
