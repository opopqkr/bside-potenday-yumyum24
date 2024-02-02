package best.bside.potenday.yumyum24.service;


import best.bside.potenday.yumyum24.domain.Bookmark;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.UserBookmarkInfo;
import best.bside.potenday.yumyum24.repository.BookmarkRepository;
import best.bside.potenday.yumyum24.repository.ComboItemProductRepository;
import best.bside.potenday.yumyum24.repository.ComboItemRepository;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ComboItemProductRepository comboItemProductRepository;
    private final ComboItemRepository comboItemRepository;
    private final UserRepository userRepository;

    public Page<UserBookmarkInfo> getUserBookmarkInfo(String name, String category, PageInfo pageInfo) {
        final User user = userRepository.findByEmail(name);
        Long userId = user.getUserId();

        final Page<UserBookmarkInfo> userBookMarkInfoList = bookmarkRepository.getUserBookmarkInfo(userId, category, pageInfo);

        final List<UserBookmarkInfo> list = userBookMarkInfoList.getResult();

        if (userBookMarkInfoList != null && userBookMarkInfoList.getPageInfo().getTotalPage() != 0) {
            for (UserBookmarkInfo userBookmarkInfo : list) {
                userBookmarkInfo.setProducts(comboItemProductRepository.findProductByComboItemId(userBookmarkInfo.getComboItemId()));
            }
        }
        return userBookMarkInfoList;
    }

    @Transactional
    public UserBookmarkInfo toggleUserBookmark(String userName, Long comboItemId) {

        final User user = userRepository.findByEmail(userName);
        Long userId = user.getUserId();

        // 현재 상태 조회
        String isBookMarked = bookmarkRepository.getUserBookmarkStatus(userId, comboItemId);

        UserBookmarkInfo userBookmarkInfo;
        if (Objects.isNull(isBookMarked)) {
            ComboItemInfo comboItemInfo = comboItemRepository.findComboItemInfoByComboItemId(comboItemId);
            bookmarkRepository.save(Bookmark.builder()
                    .userId(userId)
                    .comboItemName(comboItemInfo.getName())
                    .category(comboItemInfo.getCategory())
                    .comboItemId(comboItemId)
                    .isBookmarked("Y").build());

            userBookmarkInfo = new UserBookmarkInfo(userId, comboItemId, comboItemInfo.getName(),
                    comboItemInfo.getCategory(), "Y");
        } else {
            userBookmarkInfo = bookmarkRepository.toggleUserBookmark(userId, comboItemId, isBookMarked);
        }

        if (userBookmarkInfo.getIsBookMarked().equals("Y")) {
            // 조회 수 증가
            comboItemRepository.updateComboItemGoodCount(comboItemId,1);// 증가
        } else {
            // 감소
            comboItemRepository.updateComboItemGoodCount(comboItemId,-1);// 증가
        }

        return userBookmarkInfo;
    }
}
