package com.github.receipes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundExceptionException(Exception e) {
        return handleException(e, NOT_FOUND, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleLucyFunctionalError(BadRequestException e) {
        return handleException(e, BAD_REQUEST, singletonList(e.getError()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorResponse> errors = ex.getFieldErrors().stream()
                .map(fieldError -> ErrorResponse.builder()
                        .code("INVALID_FIELD")
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return handleException(ex, BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleDefaultException(Exception e) {
        return handleException(e, INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus status, List<ErrorResponse> errors) {
        log.debug("Error occurred handling the request. Error response: {}, Cause: {}", errors, ex);
        log.error("Error occurred handling the request. Error response: {}, Cause: {}", errors, ex.getLocalizedMessage());

        return new ResponseEntity<>(errors, new HttpHeaders(), status);
    }
}