package best.bside.potenday.yumyum24.enums;

import lombok.Getter;

@Getter
public enum Category implements CodeValue {

    FOOD("F", "FOOD"),
    DRINK("D", "DRINK");

    private String code;

    private String value;

    Category(String code, String value) {
        this.code = code;
        this.value = value;
    }
}






