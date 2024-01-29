package best.bside.potenday.yumyum24.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {

    private String object;

    private String field;

    private Object rejectedValue;

    private String message;

    public ValidationError(FieldError error) {
        this.object = error.getObjectName();
        this.field = error.getField();
        this.rejectedValue = error.getRejectedValue();
        this.message = error.getDefaultMessage();
    }
}
