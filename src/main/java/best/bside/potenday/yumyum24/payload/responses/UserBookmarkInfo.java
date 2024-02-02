package best.bside.potenday.yumyum24.payload.responses;


import best.bside.potenday.yumyum24.domain.Product;
import best.bside.potenday.yumyum24.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserBookmarkInfo {

    private Long userId;
    private Long comboItemId;
    private String comboItemName;
    private Category category;
    private String isBookMarked;

    @Setter
    private List<Product> products;

    public UserBookmarkInfo(Long userId, Long name, String comboItemName, Category category, String isBookMarked) {
        this.userId = userId;
        this.comboItemId = name;
        this.comboItemName = comboItemName;
        this.category = category;
        this.isBookMarked= isBookMarked;
    }

}
