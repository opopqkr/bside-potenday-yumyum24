package best.bside.potenday.yumyum24.payload;

import best.bside.potenday.yumyum24.config.databind.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class Response<T> {

    private final static String IS_SUCCESS = "SUCCESS";

    private final static String IS_FAIL = "FAIL";

    private final int status;

    private final String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime timestamp;

    private T data;

    public Response(HttpStatus status) {
        this.status = status.value();
        this.message = status.isError() ? IS_FAIL : IS_SUCCESS;
        this.timestamp = LocalDateTime.now();
    }

    public Response(HttpStatus status, T data) {
        this.status = status.value();
        this.message = status.isError() ? IS_FAIL : IS_SUCCESS;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }
}
