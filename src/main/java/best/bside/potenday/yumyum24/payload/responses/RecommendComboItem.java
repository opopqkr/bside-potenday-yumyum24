package best.bside.potenday.yumyum24.payload.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecommendComboItem {

    private String ment;

    private ComboItemInfo foodComboItem;

    private ComboItemInfo drinkComboItem;

}
