package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

}