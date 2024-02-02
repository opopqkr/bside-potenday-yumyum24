package best.bside.potenday.yumyum24.config.exception;

import best.bside.potenday.yumyum24.payload.Response;
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
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<Response<String>> buildResponseEntity(HttpStatus status, String detailMessage) {
        final Response<String> response = new Response<>(status, detailMessage);
        return ResponseEntity.status(status.value()).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Response<String>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        final String detailMessage = String.format("%s parameter is missing - %s", ex.getParameterName(), ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(BAD_REQUEST, detailMessage);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Response<String>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        final String mediaTypes = ex.getSupportedMediaTypes()
                .stream()
                .map(MediaType::toString)
                .collect(Collectors.joining(", "));

        final String detailMessage = String.format("%s media type is not supported. Supported media types are %s",
                ex.getContentType(), mediaTypes);
        log.error(detailMessage, ex);

        return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE, detailMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Response<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final String fields = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        String defaultMessage = String.format("Validation error - %s", fields);

        return buildResponseEntity(BAD_REQUEST, defaultMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Response<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        final String detailMessage = String.format("Malformed JSON request - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(BAD_REQUEST, detailMessage);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    protected ResponseEntity<Response<String>> handleHttpMessageNotWritable(HttpMessageNotWritableException ex) {
        final String detailMessage = String.format("Error writing JSON output - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(BAD_REQUEST, detailMessage);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Response<String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        final String detailMessage = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL());
        log.error(detailMessage, ex);

        return buildResponseEntity(BAD_REQUEST, detailMessage);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Response<String>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        final String detailMessage = String.format("Request method '%s' Not supported", ex.getMethod());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    protected ResponseEntity<Response<String>> handleConversionNotSupported(ConversionNotSupportedException ex) {
        final String detailMessage = String.format("Conversion Not Supported - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<Response<String>> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        final String detailMessage = String.format("Http Media Type Not Acceptable - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<Response<String>> handleMissingPathVariable(MissingPathVariableException ex) {
        final String detailMessage = String.format("Missing Path Variable - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    protected ResponseEntity<Response<String>> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        final String detailMessage = String.format("Missing Servlet Request Part - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<Response<String>> handleServletRequestBindingException(ServletRequestBindingException ex) {
        final String detailMessage = String.format("Servlet Request Binding Exception - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Response<String>> handleTypeMismatch(TypeMismatchException ex) {
        final String detailMessage = String.format("Type Mismatch - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(METHOD_NOT_ALLOWED, detailMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Response<String>> handleAuthentication(AuthenticationException ex) {
        final String detailMessage = String.format("Unauthorized - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(UNAUTHORIZED, detailMessage);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Response<String>> handleValidationException(ValidationException ex) {
        final String detailMessage = String.format("Validation error - %s", ex.getMessage());
        log.error(detailMessage, ex);

        return buildResponseEntity(BAD_REQUEST, detailMessage);
    }
}
