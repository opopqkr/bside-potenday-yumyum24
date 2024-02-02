package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Product;
import best.bside.potenday.yumyum24.domain.QComboItem;
import best.bside.potenday.yumyum24.domain.QComboItemProduct;
import best.bside.potenday.yumyum24.domain.QProduct;

import java.util.List;

public class ComboItemProductRepositoryCustomImpl extends BaseRepository implements ComboItemProductRepositoryCustom {

    @Override
    public List<Product> findProductByComboItemId(Long comboItemId) {
        QProduct p = QProduct.product;
        QComboItemProduct cp = QComboItemProduct.comboItemProduct;

        return selectFrom(p).
                where(p.productId.in(select(cp.productId)
                        .from(cp)
                        .where(cp.comboItemId.eq(comboItemId))
                        .fetchAll()))
                .fetch();
    }

    @Override
    public List<String> findComboItemNameByProductId(Long productId) {
        QComboItem c = QComboItem.comboItem;
        QComboItemProduct cp = QComboItemProduct.comboItemProduct;

        return select(c.name)
                .from(c)
                .where(c.comboItemId.in(select(cp.comboItemId)
                        .from(cp)
                        .where(cp.productId.eq(productId))
                        .limit(3)
                        .fetch()))
                .fetch();
    }
}
