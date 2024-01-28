package best.bside.potenday.yumyum24.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, WebRequest request, String message, Throwable ex) {
        return buildResponseEntity(status, request, message, ex, null);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, WebRequest request, String message
            , Throwable ex, List<ApiValidationError> subErrors) {
        final ServletWebRequest req = ((ServletWebRequest) request);

        final ApiError error = new ApiError(status, Objects.requireNonNull(req.getHttpMethod()), message, ex);

        if (Objects.nonNull(subErrors) && !subErrors.isEmpty()) error.setSubErrors(subErrors);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("%s parameter is missing - %s", ex.getParameterName(), ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String mediaTypes = ex.getSupportedMediaTypes()
                .stream()
                .map(MediaType::toString)
                .collect(Collectors.joining(", "));
        final String message = String.format("%s media type is not supported. Supported media types are %s", ex.getContentType(), mediaTypes);
        log.error(message, ex);

        return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String fields = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        final String message = String.format("Validation error - %s", fields);
        log.error(message);

        final List<ApiValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(ApiValidationError::new)
                .collect(Collectors.toList());

        return buildResponseEntity(BAD_REQUEST, request, message, ex, errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Malformed JSON request - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Error writing JSON output - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Request method '%s' Not supported", ex.getMethod());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Conversion Not Supported - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Http Media Type Not Acceptable - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Missing Path Variable - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Missing Servlet Request Part - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Servlet Request Binding Exception - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Type Mismatch - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(status, request, message, ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthentication(AuthenticationException ex, WebRequest request) {
        final String message = String.format("Unauthorized - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(UNAUTHORIZED, request, message, ex);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        final String message = String.format("Validation error - %s", ex.getMessage());
        log.error(message, ex);

        return buildResponseEntity(BAD_REQUEST, request, message, ex);
    }
}
