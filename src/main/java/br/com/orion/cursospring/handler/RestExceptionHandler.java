package br.com.orion.cursospring.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.orion.cursospring.error.ResourceNotFoundDetails;
import br.com.orion.cursospring.error.ResourceNotFoundException;

/**
 * RestExceptionHandler
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ResourceNotFoundDetails details = ResourceNotFoundDetails.Builder.newBuider()
            .timestamp(new Date().getTime())
            .status(HttpStatus.NOT_FOUND.value())
            .title("Resource not found")
            .detail(rfnException.getMessage())
            .developerMessage(rfnException.getClass().getName())
            .build();
            
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

}