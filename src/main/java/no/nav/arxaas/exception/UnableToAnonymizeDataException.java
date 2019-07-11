package no.nav.arxaas.exception;

public class UnableToAnonymizeDataException extends RuntimeException {
    public UnableToAnonymizeDataException(String errorMessage) {
        super(errorMessage);
    }
}
