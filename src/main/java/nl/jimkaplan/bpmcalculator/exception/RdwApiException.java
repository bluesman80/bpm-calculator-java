package nl.jimkaplan.bpmcalculator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RdwApiException extends Exception {
    private final HttpStatus statusCode;

    public RdwApiException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}