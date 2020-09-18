package ru.orlovs.handbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.orlovs.handbook.dto.ErrorMessage;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Set<ErrorMessage> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ErrorMessage> result = new HashSet<>();

        ex.getConstraintViolations().forEach((err) ->
                result.add(new ErrorMessage(err.getMessage()))
        );
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public Set<ErrorMessage> handleValidationExceptions(RepositoryConstraintViolationException ex) {
        Set<ErrorMessage> result = new HashSet<>();

        ex.getErrors().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String msg = err.getDefaultMessage();
            result.add(new ErrorMessage(msg, field));
        });
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Set<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<ErrorMessage> result = new HashSet<>();

        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String msg = err.getDefaultMessage();
            result.add(new ErrorMessage(msg, field));
        });
        return result;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Set<ErrorMessage> handleOtherExceptions(Exception ex) {
        log.error(ex);

        String msg = ex.getMessage();
        return Collections.singleton(new ErrorMessage(msg));
    }
}
