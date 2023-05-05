package ro.marcc.server.exception;

public class MeciExpectationFailedException extends RuntimeException{
    public MeciExpectationFailedException(String message) {
        super(message);
    }
}
