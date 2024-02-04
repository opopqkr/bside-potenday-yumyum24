package best.bside.potenday.yumyum24.repository;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<Long> findComboItemIdByUserIdOrderBySavedAtDesc(Long userId);

}
