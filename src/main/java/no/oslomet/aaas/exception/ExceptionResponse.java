package no.oslomet.aaas.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Response to client when a exception occurs in the service
 */
public class ExceptionResponse {

    private final Date timestamp;
    private final String message;
    private final String details;
    private final HttpStatus status;

    public ExceptionResponse(Date timestamp, String message, String details, HttpStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
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

    public HttpStatus getStatus() {
        return status;
    }
}
