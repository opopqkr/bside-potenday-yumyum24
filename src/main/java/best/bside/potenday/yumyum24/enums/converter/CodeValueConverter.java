package best.bside.potenday.yumyum24.enums.converter;

import best.bside.potenday.yumyum24.enums.CodeValue;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class CodeValueConverter<E extends Enum<E> & CodeValue> implements AttributeConverter<E, String> {

    private Class<E> clazz;

    CodeValueConverter(Class<E> enumClazz) {
        this.clazz = enumClazz;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(clazz).stream()
                .filter(e -> e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}
