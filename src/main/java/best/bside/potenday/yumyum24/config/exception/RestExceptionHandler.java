package best.bside.potenday.yumyum24.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, List<ValidationError> validationErrors) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(status, message);

        if (Objects.nonNull(validationErrors) && !validationErrors.isEmpty())
            exceptionResponse.setValidationErrors(validationErrors);

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        final String message = String.format("%s parameter is missing - %s", ex.getParameterName(), ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, message, null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        final String mediaTypes = ex.getSupportedMediaTypes()
                .stream()
                .map(MediaType::toString)
                .collect(Collectors.joining(", "));

        final String message = String.format("%s media type is not supported. Supported media types are %s",
                ex.getContentType(), mediaTypes);
        log.error(message, ex);

        return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE, message, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final String fields = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        final String message = String.format("Validation error - %s", fields);
        log.error(message);

        final List<ValidationError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .collect(Collectors.toList());

        return buildResponseEntity(BAD_REQUEST, message, fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        final String message = String.format("Malformed JSON request - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, message, null);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex) {
        final String message = String.format("Error writing JSON output - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, message, null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        final String message = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, message, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        final String message = String.format("Request method '%s' Not supported", ex.getMethod());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex) {
        final String message = String.format("Conversion Not Supported - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        final String message = String.format("Http Media Type Not Acceptable - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex) {
        final String message = String.format("Missing Path Variable - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        final String message = String.format("Missing Servlet Request Part - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex) {
        final String message = String.format("Servlet Request Binding Exception - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex) {
        final String message = String.format("Type Mismatch - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, message, null);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthentication(AuthenticationException ex) {
        final String message = String.format("Unauthorized - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(UNAUTHORIZED, message, null);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
        final String message = String.format("Validation error - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, message, null);
    }
}
