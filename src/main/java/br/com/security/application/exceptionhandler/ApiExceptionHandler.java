package br.com.security.application.exceptionhandler;

import br.com.security.application.exceptionhandler.exception.AuthorizationException;
import br.com.security.application.exceptionhandler.exception.ObjectNotFoundException;
import br.com.security.application.exceptionhandler.helper.StandardError;
import br.com.security.application.exceptionhandler.helper.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Elvis Fernandes on 01/11/19
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = messageSource.getMessage(e.getMessage(), null, request.getLocale());
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Internal Exception", message,((HttpServletRequest) request).getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        String message = messageSource.getMessage(e.getMessage(), e.getParams(), request.getLocale());
        StandardError err = new StandardError(System.currentTimeMillis(), NOT_FOUND.value(), "Not found", message, request.getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ValidationError err = new ValidationError(System.currentTimeMillis(), UNPROCESSABLE_ENTITY.value(), "Validation error", e.getMessage(), ((HttpServletRequest) request).getRequestURI(), e.getClass().getName());
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(), BAD_REQUEST.value(), "Data Integrity", e.getMessage(), request.getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(BAD_REQUEST).body(err);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<StandardError> emptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(), NOT_FOUND.value(), "Not found", e.getMessage(), request.getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<StandardError> runtimeException(RuntimeException e, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(), INTERNAL_SERVER_ERROR.value(), "Unexpected error", e.getMessage(), request.getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorizationException(AuthorizationException e, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(), FORBIDDEN.value(), "Access Denied", e.getMessage(), request.getRequestURI(), e.getClass().getName());
        return ResponseEntity.status(FORBIDDEN).body(err);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), METHOD_NOT_ALLOWED.value(), "Method Not Allowed", ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getClass().getName());
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(err);
    }
}
