package ro.marcc.server.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.marcc.server.exception.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    public record ErrorResponse(String errorCode, String errorMessage, List<String> errors) {
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return new ResponseEntity<>(new ErrorResponse("Eroare Validata", "Eroare Validare",
                fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList())), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UtilizatorNotFoundException.class)
    public String methodArgumentException(UtilizatorNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SponsorNotFoundException.class)
    public String methodArgumentException(SponsorNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(UtilizatorExpectationFailedException.class)
    public String methodArgumentException(UtilizatorExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StireNotFoundException.class)
    public String methodArgumentException(StireNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(StireExpectationFailedException.class)
    public String methodArgumentException(StireExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonalNotFoundException.class)
    public String methodArgumentException(PersonalNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(PersonalExpectationFailedException.class)
    public String methodArgumentException(PersonalExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MeciNotFoundException.class)
    public String methodArgumentException(MeciNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MeciExpectationFailedException.class)
    public String methodArgumentException(MeciExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(VoleiJuvenilExpectationFailedException.class)
    public String methodArgumentException(VoleiJuvenilExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VoleiJuvenitNotFoundException.class)
    public String methodArgumentException(VoleiJuvenitNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(LocatieExpectationFailedException.class)
    public String methodArgumentException(LocatieExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LocatieNotFoundException.class)
    public String methodArgumentException(LocatieNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DetaliiClubNotFoundException.class)
    public String methodArgumentException(DetaliiClubNotFoundException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(DetaliiClubExpectationFailedException.class)
    public String methodArgumentException(DetaliiClubExpectationFailedException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }


}