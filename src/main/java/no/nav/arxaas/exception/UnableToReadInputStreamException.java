package no.nav.arxaas.exception;

public class UnableToReadInputStreamException extends RuntimeException{
    public UnableToReadInputStreamException(String errorMessage){
        super(errorMessage);
    }
}
