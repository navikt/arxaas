package no.oslomet.aaas.controller;

import no.oslomet.aaas.exception.ExceptionResponse;
import no.oslomet.aaas.exception.UnableToAnonymizeDataInvalidDataSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import no.oslomet.aaas.exception.UnableToAnonymizeDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Intercepts Exceptions thrown in the service. Ensures a uniform response format and that a correct HTTP status is set
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handles all exceptions thrown unless cached by a more specific handler
     * @param ex Exception thrown
     * @param request WebRequest from client
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptionAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception.class error, HttpStatus: INTERNAL_SERVER_ERROR, ExceptionToString: ", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.toString(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> handleMethodNotSupportedExceptions(Exception ex, WebRequest request) {
        logger.error("Exception error:Exception thrown when a request handler does not support a specific request method. HttpStatus: METHOD_NOT_ALLOWED, ExceptionToString: ", ex);

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentExceptions (IllegalArgumentException ex, WebRequest request){
        logger.error("Exception error:Thrown to indicate that a method has been passed an illegal or inappropriate argument. HttpStatus: BAD_REQUEST, ExceptionToString: ", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        logger.error("Exception error:Exception to be thrown when validation on an argument annotated with @Valid fails. HttpStatus: BAD_REQUEST, ExceptionToString: ", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToAnonymizeDataException.class)
    public ResponseEntity<Object> handleUnableToAnonymizeDataException(UnableToAnonymizeDataException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.toString() + ", " +
                        "Unable to anonymize the dataset with the provided attributes and hierarchies." +
                " A common cause of this error is more than one QUASIIDENTIFYING attribute without a hierarchy",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToAnonymizeDataInvalidDataSetException.class)
    public ResponseEntity<Object> handleUnableToAnonymizeDataInvalidDataSetException(UnableToAnonymizeDataInvalidDataSetException ex,  WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}