package no.oslomet.aaas.exception;

public class UnableToAnonymizeDataException extends RuntimeException {
    public UnableToAnonymizeDataException(String errorMessage) {
        super(errorMessage);
    }
}
