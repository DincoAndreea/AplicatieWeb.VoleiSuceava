package ro.marcc.server.exception;

public class UtilizatorNotFoundException extends RuntimeException{
    public UtilizatorNotFoundException(String message) {
        super(message);
    }
}
