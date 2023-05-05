package ro.marcc.server.exception;

public class PersonalNotFoundException extends RuntimeException{
    public PersonalNotFoundException(String message) {
        super(message);
    }
}
