package best.bside.potenday.yumyum24.config.exception;

import best.bside.potenday.yumyum24.config.databind.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private final int status;

    private final String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime timestamp;

    @Setter
    private List<ValidationError> validationErrors;

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
