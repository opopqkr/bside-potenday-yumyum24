package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QComboItem;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;

import java.util.List;

public class ComboItemRepositoryCustomImpl extends BaseRepository implements ComboItemRepositoryCustom {

    @Override
    public ComboItemInfo findByCategory(Category category) {
        QComboItem c = QComboItem.comboItem;

        return select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.name,
                c.isGoodCount))
                .from(c)
                .where(c.category.eq(category))
                .orderBy(Expressions
                        .numberTemplate(Double.class, "function('rand')").asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<ComboItemInfo> findOrderByRandom() {
        QComboItem c = QComboItem.comboItem;

        return select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.name,
                c.isGoodCount))
                .from(c)
                .orderBy(Expressions
                        .numberTemplate(Double.class, "function('rand')").asc())
                .limit(5)
                .fetch();
    }
}
