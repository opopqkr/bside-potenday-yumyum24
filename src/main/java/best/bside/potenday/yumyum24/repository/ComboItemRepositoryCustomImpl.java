package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QComboItem;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ComboItemRepositoryCustomImpl extends BaseRepository implements ComboItemRepositoryCustom {

    @Override
    public ComboItemInfo findByCategory(Category category) {
        QComboItem c = QComboItem.comboItem;

        return select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.category,
                c.name,
                c.review,
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
                c.category,
                c.name,
                c.review,
                c.isGoodCount))
                .from(c)
                .orderBy(Expressions
                        .numberTemplate(Double.class, "function('rand')").asc())
                .limit(5)
                .fetch();
    }

    @Override
    public Page<ComboItemInfo> findByCategoryOrderBySortByPageInfo(String category, String sortBy, PageInfo pageInfo) {
        QComboItem c = QComboItem.comboItem;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.equals("F", category)) {
            booleanBuilder.and(c.category.eq(Category.FOOD));
        } else if (StringUtils.equals("D", category)) {
            booleanBuilder.and(c.category.eq(Category.DRINK));
        }

        OrderSpecifier<?> orderBy = c.comboItemId.desc();
        if (StringUtils.equals(sortBy, "TOP")) {
            orderBy = c.isGoodCount.desc();
        } else if (StringUtils.equals(sortBy, "NEW")) {
            orderBy = c.createdAt.desc();
        } else if (StringUtils.equals(sortBy, "REPLY")) {

        }

        final List<ComboItemInfo> list = select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.category,
                c.name,
                c.review,
                c.isGoodCount))
                .from(c)
                .where(booleanBuilder)
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .orderBy(orderBy)
                .fetch();

        pageInfo.setTotalItemCount(select(c.count()).from(c).where(booleanBuilder).fetchOne());
        return new Page<>(list, pageInfo);
    }
}
