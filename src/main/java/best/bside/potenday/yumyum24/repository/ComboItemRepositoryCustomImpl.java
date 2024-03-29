package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QComboItem;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
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
                c.bookmarkCount,
                c.replyCount))
                .from(c)
                .where(c.category.eq(category))
                .orderBy(orderByRandom())
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
                c.bookmarkCount,
                c.replyCount))
                .from(c)
                .orderBy(orderByRandom())
                .limit(5)
                .fetch();
    }

    @Override
    public Page<ComboItemInfo> findByCategoryOrderBySortByPageInfo(String category, String sortBy, PageInfo pageInfo) {
        QComboItem c = QComboItem.comboItem;

        final List<ComboItemInfo> list = select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.category,
                c.name,
                c.review,
                c.bookmarkCount,
                c.replyCount))
                .from(c)
                .where(conditionByCategory(c, category))
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .orderBy(orderBy(c, sortBy))
                .fetch();

        Long totalItemCount = select(c.count()).from(c).where(conditionByCategory(c, category)).fetchOne();
        pageInfo.setTotalItemCount(totalItemCount == null ? 0 : totalItemCount);
        return new Page<>(list, pageInfo);
    }

    @Override
    public Page<ComboItemInfo> findByUserBookmarkedComboItemPageInfo(List<Long> comboItemIds, String category, PageInfo pageInfo) {
        QComboItem c = QComboItem.comboItem;

        final List<ComboItemInfo> list = select(Projections.constructor(ComboItemInfo.class,
                c.comboItemId,
                c.category,
                c.name,
                c.review,
                c.bookmarkCount,
                c.replyCount))
                .from(c)
                .where(c.comboItemId.in(comboItemIds)
                        .and(conditionByCategory(c, category)))
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .fetch();

        Long totalItemCount = select(c.count())
                .from(c)
                .where(c.comboItemId.in(comboItemIds)
                        .and(conditionByCategory(c, category)))
                .fetchOne();
        pageInfo.setTotalItemCount(totalItemCount == null ? 0 : totalItemCount);
        return new Page<>(list, pageInfo);
    }

    @Override
    public void updateComboItemBookmarkCount(Long comboItemId, Long value) {
        QComboItem c = QComboItem.comboItem;

        update(c)
                .set(c.bookmarkCount, c.bookmarkCount.add(value))
                .where(c.comboItemId.eq(comboItemId))
                .execute();
    }

    @Override
    public void updateComboItemReplyCount(Long comboItemId, Long value) {
        QComboItem c = QComboItem.comboItem;

        update(c)
                .set(c.replyCount, c.replyCount.add(value))
                .where(c.comboItemId.eq(comboItemId))
                .execute();
    }

    private BooleanBuilder conditionByCategory(QComboItem c, String category) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.equals(Category.FOOD.getCode(), category)) {
            booleanBuilder.and(c.category.eq(Category.FOOD));
        } else if (StringUtils.equals(Category.DRINK.getCode(), category)) {
            booleanBuilder.and(c.category.eq(Category.DRINK));
        } else {
            booleanBuilder = null;
        }

        return booleanBuilder;
    }

    private OrderSpecifier<?> orderByRandom() {
        return Expressions.numberTemplate(Double.class, "function('rand')").asc();
    }

    private OrderSpecifier<?> orderBy(QComboItem c, String sortBy) {
        if (StringUtils.isBlank(sortBy))
            return new OrderSpecifier<>(Order.DESC, c.comboItemId);

        switch (sortBy) {
            case "TOP":
                return new OrderSpecifier<>(Order.DESC, c.bookmarkCount);
            case "NEW":
                return new OrderSpecifier<>(Order.DESC, c.createdAt);
            case "REPLY":
                return new OrderSpecifier<>(Order.DESC, c.replyCount);
        }

        return null;
    }
}
