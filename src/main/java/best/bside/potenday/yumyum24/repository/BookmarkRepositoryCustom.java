package best.bside.potenday.yumyum24.repository;


import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.UserBookmarkInfo;


public interface BookmarkRepositoryCustom {

    Page<UserBookmarkInfo> getUserBookmarkInfo(Long userId, String category, PageInfo pageInfo);

    String getUserBookmarkStatus(Long userId, Long comboItemId);

    UserBookmarkInfo toggleUserBookmark(Long userId, Long comboItemId, String currentIsBookmarked);

}
