package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;

import java.util.List;

public interface ComboItemRepositoryCustom {

    ComboItemInfo findByCategory(Category category);

    List<ComboItemInfo> findOrderByRandom();

    Page<ComboItemInfo> findByCategoryOrderBySortByPageInfo(String category, String sortBy, PageInfo pageInfo);

    Page<ComboItemInfo> findByUserBookmarkedComboItemPageInfo(List<Long> comboItemIds, String category, PageInfo pageInfo);

    void updateComboItemBookmarkCount(Long comboItemId, Long value);

}
