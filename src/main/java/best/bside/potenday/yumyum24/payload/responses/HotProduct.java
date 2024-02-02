package best.bside.potenday.yumyum24.payload.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HotProduct {

    private Long productId;

    private String productType;

    private String name;

    private String imageUrl;

    private Long usedCount;

    @Setter
    private List<String> comboItemName;

    public HotProduct(Long productId, String productType, String name, String imageUrl, Long usedCount) {
        this.productId = productId;
        this.productType = productType;
        this.name = name;
        this.imageUrl = imageUrl;
        this.usedCount = usedCount;
    }
}
