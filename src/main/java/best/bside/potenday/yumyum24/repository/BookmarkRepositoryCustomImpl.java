package best.bside.potenday.yumyum24.repository;


import best.bside.potenday.yumyum24.domain.QBookmark;
import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.UserBookmarkInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BookmarkRepositoryCustomImpl extends BaseRepository implements BookmarkRepositoryCustom {

    @Override
    public Page<UserBookmarkInfo> getUserBookmarkInfo(Long userId, String category, PageInfo pageInfo) {
        QBookmark b = QBookmark.bookmark;
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.equals("FOOD", category)) {
            whereClause.and(b.category.eq(Category.FOOD));
        } else if (StringUtils.equals("DRINK", category)) {
            whereClause.and(b.category.eq(Category.DRINK));
        }
        final List<UserBookmarkInfo> list = select(Projections.constructor(UserBookmarkInfo.class,
                b.userId, b.comboItemId, b.comboItemName, b.category, b.isBookmarked))
                .from(b)
                .where(whereClause.and(b.isBookmarked.eq("Y")))
                .orderBy(b.comboItemId.asc())
                .fetch();

        pageInfo.setTotalItemCount(select(b.count()).from(b).where(whereClause).fetchOne());
        return new Page<>(list, pageInfo);
    }

    @Override
    public String getUserBookmarkStatus(Long userId, Long comboItemId) {

        QBookmark b = QBookmark.bookmark;
        return select(b.isBookmarked)
                .from(b)
                .where(b.userId.eq(userId).and(b.comboItemId.eq(comboItemId)))
                .fetchOne();
    }

    @Override
    public UserBookmarkInfo toggleUserBookmark(Long userId, Long comboItemId, String currentIsBookmarked) {

        QBookmark b = QBookmark.bookmark;

        // N이 아니라면, 기존 Y
        String newIsBookmarked = "N".equals(currentIsBookmarked) ? "Y" : "N";

        update(b)
                .set(b.isBookmarked, newIsBookmarked)
                .where(b.userId.eq(userId).and(b.comboItemId.eq(comboItemId)))
                .execute();

        return select(Projections.constructor(UserBookmarkInfo.class,
                b.userId, b.comboItemId, b.comboItemName, b.category, b.isBookmarked))
                .from(b)
                .where(b.userId.eq(userId).and(b.comboItemId.eq(comboItemId)))
                .fetchOne();
    }
}
