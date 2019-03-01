package no.oslomet.aaas.exception;

public class AaaSRuntimeException extends RuntimeException {
    public AaaSRuntimeException(String errorMessage) {
        super(errorMessage);
    }
}
