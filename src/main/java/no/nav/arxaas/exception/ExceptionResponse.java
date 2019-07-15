package no.nav.arxaas.exception;

import java.util.Date;

/**
 * Response to client when a exception occurs in the service
 */
public class ExceptionResponse {

    private final Date timestamp;
    private final String message;
    private final String details;


    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public static ExceptionResponse now(String message, String details){
        return new ExceptionResponse(new Date(), message, details);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
