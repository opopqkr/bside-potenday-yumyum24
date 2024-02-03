package best.bside.potenday.yumyum24.payload.responses;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.domain.Product;
import best.bside.potenday.yumyum24.enums.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComboItemInfo {

    private Long comboItemId;

    private String name;

    private Category category;

    private String review;

    private long isGoodCount;

    private long replyCount;

    @Setter
    private List<Product> products;

    @Setter
    private List<ComboItemDescription> comboItemDescriptions;

    public ComboItemInfo(Long comboItemId, Category category, String name, String review, long isGoodCount, long replyCount) {
        this.comboItemId = comboItemId;
        this.category = category;
        this.review = review;
        this.name = name;
        this.isGoodCount = isGoodCount;
        this.replyCount = replyCount;
    }
}
