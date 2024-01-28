package best.bside.potenday.yumyum24.config.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    
    @Override
    public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long millis = value.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        jsonGenerator.writeString(String.valueOf(millis));
    }
}
