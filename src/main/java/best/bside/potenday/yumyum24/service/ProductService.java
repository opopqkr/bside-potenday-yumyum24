package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.payload.responses.TopProduct;
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

    public List<TopProduct> getTopProducts() {
        final List<TopProduct> topProducts = productRepository.findOrderByUsedCount();

        if (topProducts != null && !topProducts.isEmpty()) {
            for (TopProduct hotProduct : topProducts) {
                hotProduct.setComboItemName(comboItemProductRepository
                        .findComboItemNameByProductId(hotProduct.getProductId()));
            }
        }

        return topProducts;
    }
}
