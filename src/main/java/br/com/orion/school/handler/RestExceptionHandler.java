package br.com.orion.school.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.orion.school.error.ErrorDetails;
import br.com.orion.school.error.ResourceNotFoundDetails;
import br.com.orion.school.error.ResourceNotFoundException;
import br.com.orion.school.error.ValidationErrorDetails;

/**
 * RestExceptionHandler
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ResourceNotFoundDetails details = ResourceNotFoundDetails.Builder
        .newBuilder().timestamp(new Date().getTime())
        .status(HttpStatus.NOT_FOUND.value())
        .title("Resource not found")
        .detail(rfnException.getMessage())
        .developerMessage(rfnException.getClass().getName())
        .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : fieldErros) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }

        ValidationErrorDetails details = ValidationErrorDetails.Builder
        .newBuilder()
        .timestamp(new Date()
        .getTime())
        .status(HttpStatus.BAD_REQUEST.value()).title("Argument not valid")
        .detail("Argument invalid in fields")
        .addError(errors).developerMessage(exception.getClass().getName())
        .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetails details = ErrorDetails.Builder
        .newBuilder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Resource not found")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .build();
                
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException exception) {
        ErrorDetails details = ErrorDetails.Builder
        .newBuilder().timestamp(new Date().getTime())
        .status(HttpStatus.NOT_FOUND.value())
        .title("Reference not found")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetails details = ErrorDetails.Builder.newBuilder().timestamp(new Date().getTime()).status(status.value())
                .title("Internal error").detail(exception.getMessage()).developerMessage(exception.getClass().getName())
                .build();

        return new ResponseEntity<>(details, status);
    }
    
    

}