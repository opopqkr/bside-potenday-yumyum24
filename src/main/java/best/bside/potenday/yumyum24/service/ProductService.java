package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.payload.responses.HotProduct;
import best.bside.potenday.yumyum24.repository.ComboItemProductRepository;
import best.bside.potenday.yumyum24.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ComboItemProductRepository comboItemProductRepository;

    public List<HotProduct> getHotProducts() {
        final List<HotProduct> hotProducts = productRepository.findOrderByUsedCount();

        if (hotProducts != null && !hotProducts.isEmpty()) {
            for (HotProduct hotProduct : hotProducts) {
                hotProduct.setComboItemName(comboItemProductRepository
                        .findComboItemNameByProductId(hotProduct.getProductId()));
            }
        }

        return hotProducts;
    }
}
