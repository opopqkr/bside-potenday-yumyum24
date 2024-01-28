package best.bside.potenday.yumyum24.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiValidationError {

    private String object;

    private String field;

    private Object rejectedValue;

    private String message;

    public ApiValidationError(FieldError error) {
        this.object = error.getObjectName();
        this.field = error.getField();
        this.message = error.getDefaultMessage();
        this.rejectedValue = error.getRejectedValue();
    }
}
