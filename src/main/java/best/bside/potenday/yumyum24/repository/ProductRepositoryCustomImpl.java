package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QProduct;
import best.bside.potenday.yumyum24.payload.responses.TopProduct;
import com.querydsl.core.types.Projections;

import java.util.List;

public class ProductRepositoryCustomImpl extends BaseRepository implements ProductRepositoryCustom {

    @Override
    public List<TopProduct> findOrderByUsedCount() {
        QProduct p = QProduct.product;

        return select(Projections.constructor(TopProduct.class,
                p.productId,
                p.productType,
                p.name,
                p.imageUrl,
                p.usedCount))
                .from(p)
                .orderBy(p.usedCount.desc())
                .limit(3)
                .fetch();
    }
}
