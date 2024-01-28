package best.bside.potenday.yumyum24.config.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final String value = jsonParser.getValueAsString();
        final long mills = Long.parseLong(value);
        return Instant.ofEpochMilli(mills).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }
}
