package ro.marcc.server.exception;

public class LocatieNotFoundException extends RuntimeException{
    public LocatieNotFoundException(String message) {
        super(message);
    }
}
