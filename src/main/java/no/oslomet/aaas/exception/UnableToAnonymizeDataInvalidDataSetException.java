package no.oslomet.aaas.exception;

public class UnableToAnonymizeDataInvalidDataSetException extends RuntimeException{
    public UnableToAnonymizeDataInvalidDataSetException(String errorMessage){
        super(errorMessage);
    }
}
