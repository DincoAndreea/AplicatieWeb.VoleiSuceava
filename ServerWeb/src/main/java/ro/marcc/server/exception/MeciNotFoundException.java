package ro.marcc.server.exception;

public class MeciNotFoundException extends RuntimeException{
    public MeciNotFoundException(String message) {
        super(message);
    }
}
