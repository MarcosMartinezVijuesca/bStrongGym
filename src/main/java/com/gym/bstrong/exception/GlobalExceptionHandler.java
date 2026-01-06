package com.gym.bstrong.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.error("Validation error: {}", errors);
        return new ResponseEntity<>(ErrorResponse.validationError(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        logger.error("Internal Server Error", ex);
        return new ResponseEntity<>(
                ErrorResponse.generalError(500, "Internal Server Error", "An unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException ex) {
        logger.error("Member not found exception", ex);
        return new ResponseEntity<>(ErrorResponse.notFound(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MonitorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMonitorNotFound(MonitorNotFoundException ex) {
        logger.error("Monitor not found exception", ex);
        return new ResponseEntity<>(ErrorResponse.notFound(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActivityNotFound(ActivityNotFoundException ex) {
        logger.error("Activity not found exception", ex);
        return new ResponseEntity<>(ErrorResponse.notFound(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookingNotFound(BookingNotFoundException ex) {
        logger.error("Booking not found exception", ex);
        return new ResponseEntity<>(ErrorResponse.notFound(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        logger.error("Subscription not found exception", ex);
        return new ResponseEntity<>(ErrorResponse.notFound(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
