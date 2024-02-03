package best.bside.potenday.yumyum24.service;


import best.bside.potenday.yumyum24.domain.Bookmark;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.repository.BookmarkRepository;
import best.bside.potenday.yumyum24.repository.ComboItemProductRepository;
import best.bside.potenday.yumyum24.repository.ComboItemRepository;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    private final ComboItemProductRepository comboItemProductRepository;

    private final ComboItemRepository comboItemRepository;

    private final UserRepository userRepository;

    public Page<ComboItemInfo> getUserBookmarkComboItemPageInfo(String email, String category, PageInfo pageInfo) {
        final User user = userRepository.findByEmail(email);
        final List<Long> comboItemIds
                = bookmarkRepository.findComboItemIdByUserIdOrderBySavedAtDesc(user.getUserId());

        if (ObjectUtils.isNotEmpty(comboItemIds)) {
            final Page<ComboItemInfo> userBookmarkedComboItemPageInfo =
                    comboItemRepository.findByUserBookmarkedComboItemPageInfo(comboItemIds, category, pageInfo);

            final List<ComboItemInfo> comboItemInfoList = userBookmarkedComboItemPageInfo.getResult();
            if (ObjectUtils.isNotEmpty(comboItemInfoList)) {
                for (ComboItemInfo comboItemInfo : comboItemInfoList) {
                    comboItemInfo.setProducts(comboItemProductRepository.findProductByComboItemId(comboItemInfo.getComboItemId()));
                }
            }

            return userBookmarkedComboItemPageInfo;
        } else {
            return new Page<>(null, pageInfo);
        }
    }

    public void toggleUserBookmarkComboItem(String email, Long comboItemId) {
        final User user = userRepository.findByEmail(email);
        Bookmark bookmark
                = bookmarkRepository.findByUserIdAndComboItemId(user.getUserId(), comboItemId);

        if (bookmark == null) {
            bookmark = Bookmark.builder()
                    .userId(user.getUserId())
                    .comboItemId(comboItemId)
                    .build();
            bookmark.completeSaveBookmark();
            comboItemRepository.updateComboItemBookmarkCount(comboItemId, 1L);
        } else {
            bookmark.completeModifiedBookmark();
            Long value = StringUtils.equals("Y", bookmark.getIsBookmarked()) ? -1L : 1L;
            comboItemRepository.updateComboItemBookmarkCount(comboItemId, value);
        }
    }
}
