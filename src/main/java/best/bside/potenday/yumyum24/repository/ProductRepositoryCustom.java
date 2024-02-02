package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.payload.responses.TopProduct;

import java.util.List;

public interface ProductRepositoryCustom {

    List<TopProduct> findOrderByUsedCount();

}
