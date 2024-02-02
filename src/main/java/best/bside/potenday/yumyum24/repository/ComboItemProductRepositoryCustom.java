package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Product;

import java.util.List;

public interface ComboItemProductRepositoryCustom {

    List<Product> findProductByComboItemId(Long comboItemId);

    List<String> findComboItemNameByProductId(Long productId);

}
