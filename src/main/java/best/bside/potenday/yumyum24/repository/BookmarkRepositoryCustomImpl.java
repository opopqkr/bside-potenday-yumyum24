package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QBookmark;

import java.util.List;

public class BookmarkRepositoryCustomImpl extends BaseRepository implements BookmarkRepositoryCustom {

    @Override
    public List<Long> findComboItemIdByUserIdOrderBySavedAtDesc(Long userId) {
        QBookmark b = QBookmark.bookmark;

        return select(b.comboItemId)
                .from(b)
                .where(b.userId.eq(userId))
                .fetch();
    }
}
