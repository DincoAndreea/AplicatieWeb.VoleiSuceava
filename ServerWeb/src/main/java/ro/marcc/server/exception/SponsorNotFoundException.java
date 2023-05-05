package ro.marcc.server.exception;

public class SponsorNotFoundException extends RuntimeException{
    public SponsorNotFoundException(String message) {
        super(message);
    }
}
