package no.nav.arxaas.exception;

public class UnableToAnonymizeDataInvalidDataSetException extends RuntimeException{
    public UnableToAnonymizeDataInvalidDataSetException(String errorMessage){
        super(errorMessage);
    }
}
