package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Product;
import best.bside.potenday.yumyum24.domain.QComboItemProduct;
import best.bside.potenday.yumyum24.domain.QProduct;

import java.util.List;

public class ComboItemProductRepositoryCustomImpl extends BaseRepository implements ComboItemProductRepositoryCustom {

    @Override
    public List<Product> findByComboItemId(Long comboItemId) {
        QProduct p = QProduct.product;
        QComboItemProduct cp = QComboItemProduct.comboItemProduct;

        return selectFrom(p).
                where(p.productId.in(select(cp.productId)
                        .from(cp)
                        .where(cp.comboItemId.eq(comboItemId))
                        .fetchAll()))
                .fetch();
    }
}
