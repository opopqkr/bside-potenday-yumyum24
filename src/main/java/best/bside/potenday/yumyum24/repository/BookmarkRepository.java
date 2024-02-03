package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Bookmark;
import best.bside.potenday.yumyum24.domain.pk.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

    List<Long> findComboItemIdByUserIdOrderBySavedAtDesc(Long userId);

    Bookmark findByUserIdAndComboItemId(Long userId, Long comboItemId);

}