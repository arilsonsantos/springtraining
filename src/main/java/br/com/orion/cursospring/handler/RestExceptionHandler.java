package br.com.orion.cursospring.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.orion.cursospring.error.ResourceNotFoundDetails;
import br.com.orion.cursospring.error.ResourceNotFoundException;
import br.com.orion.cursospring.error.ValidationErrorDetails;

/**
 * RestExceptionHandler
 */
@ControllerAdvice
//implements ExceptionMapper<ConstraintViolationException> 
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ResourceNotFoundDetails details = ResourceNotFoundDetails.Builder.newBuider().timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value()).title("Resource not found").detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName()).build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
        
        String fields = fieldErros.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String messages = fieldErros.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails details = ValidationErrorDetails.Builder
        .newBuider().timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Argument not valid")
                .detail("Argument not valid")
                .field(fields)
                .fieldMessage(messages)
                .developerMessage(exception.getClass()
                .getName()).build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    // @Override
    // public Response toResponse(ConstraintViolationException violations) {
    //     List<String> errors = new ArrayList<>();
	// 	violations.getConstraintViolations().stream().forEach(e -> errors.add(e.getMessage()));

	// 	//ErrorMessage errorMessage = new ErrorMessage();
	// 	//errorMessage.setErrors(errors);

	// 	//return Response.ok().status(Status.NOT_ACCEPTABLE).entity(null).build();
    //     return null;
    // }
}