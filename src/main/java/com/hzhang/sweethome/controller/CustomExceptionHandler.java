package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.exception.InvalidCategoryException;
import com.hzhang.sweethome.exception.PersonalInvoiceNotExistException;
import com.hzhang.sweethome.exception.PublicUtilsAlreadyExistsException;
import com.hzhang.sweethome.exception.PublicUtilsReservationAlreadyExistsException;
import com.hzhang.sweethome.exception.UnreadNumNotExistException;
import com.hzhang.sweethome.exception.UserAlreadyExistException;
import com.hzhang.sweethome.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<String> handleUserAlreadyExistException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistException.class)
    public final ResponseEntity<String> handleUserNotExistException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnreadNumNotExistException.class)
    public final ResponseEntity<String> handleUnreadNumNotExistException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonalInvoiceNotExistException.class)
    public final ResponseEntity<String> handlePersonalInvoiceNotExistException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PublicUtilsAlreadyExistsException.class)
    public final ResponseEntity<String> handlePublicUtilsAlreadyExistsException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PublicUtilsReservationAlreadyExistsException.class)
    public final ResponseEntity<String> handlePublicUtilsReservationAlreadyExistsException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public final ResponseEntity<String> handleInvalidCategoryException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
