package site.transcendence.userrestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.transcendence.userrestservice.api.responses.ErrorResponse;
import site.transcendence.userrestservice.api.responses.SubErrorResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_FOUND)
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordResetException.class)
    public ResponseEntity<ErrorResponse> handlePasswordResetException(PasswordResetException exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SendingEmailException.class)
    public ResponseEntity<ErrorResponse> handleSendingEmailException(SendingEmailException exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .exception(exception.getClass().getSimpleName())
                .message(null)
                .subErrors(new ArrayList<>())
                .build();

        fieldErrors.forEach(error -> {
            SubErrorResponse subError = SubErrorResponse.builder()
                    .field(error.getField())
                    .value(error.getRejectedValue().toString())
                    .message(error.getDefaultMessage())
                    .build();
            response.getSubErrors().add(subError);
        });
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
