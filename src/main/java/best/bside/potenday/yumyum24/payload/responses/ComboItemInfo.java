package best.bside.potenday.yumyum24.payload.responses;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ComboItemInfo {

    private Long comboItemId;

    private String name;

    private Long isGoodCount;

    @Setter
    private List<Product> products;

    @Setter
    private List<ComboItemDescription> comboItemDescriptions;

    public ComboItemInfo(Long comboItemId, String name, Long isGoodCount) {
        this.comboItemId = comboItemId;
        this.name = name;
        this.isGoodCount = isGoodCount;
    }


}
