package best.bside.potenday.yumyum24.payload.requests;

import best.bside.potenday.yumyum24.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class NewProduct {

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$", message = "특수 문자를 포함 할 수 없습니다.")
    private String shopType;

    @NotBlank
    private String productType;

    @NotBlank
    private String name;

    // @NotBlank
    private String imageUrl;

    @Min(value = 10, message = "10원 이상의 상품만 등록 가능 합니다.")
    private int price;

    public Product toEntity() {
        Product product = Product.builder()
                .shopType(shopType)
                .productType(productType)
                .name(name)
                .imageUrl(imageUrl == null ? null : imageUrl)
                .price(price)
                .build();

        product.completeSaveProduct();
        return product;
    }
}
