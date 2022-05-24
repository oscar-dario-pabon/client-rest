package com.globant.clientrest.exception;

import com.globant.clientrest.transfer.ExceptionResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Optional;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFound(final EntityNotFoundException ex, final WebRequest request) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder().timestamp(Calendar.getInstance()).message(ex.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAll(final Exception ex, final WebRequest request) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder().timestamp(Calendar.getInstance()).message(ex.getMessage()).details(request.getDescription(false)).build();
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder().timestamp(Calendar.getInstance()).message("Fallo en la validación de campos").details(Optional.ofNullable(ex.getFieldError()).orElseGet(
                () -> new FieldError("N/A", "N/A", ex.getBindingResult().toString())
        ).getDefaultMessage()).build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

}
