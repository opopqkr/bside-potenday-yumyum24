package best.bside.potenday.yumyum24.config.exception;

import best.bside.potenday.yumyum24.config.databind.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final String method;

    private final int status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime timestamp;

    private String message;

    @Setter
    private List<ApiValidationError> subErrors;

    @JsonIgnore
    private Throwable throwable;

    public ApiError(HttpStatus status, HttpMethod httpMethod, String message, Throwable ex) {
        this.method = httpMethod.name();
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.throwable = ex;
    }
}
