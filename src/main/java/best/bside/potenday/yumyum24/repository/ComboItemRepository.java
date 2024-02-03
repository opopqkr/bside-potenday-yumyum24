package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.ComboItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ComboItemRepository extends JpaRepository<ComboItem, Long>, ComboItemRepositoryCustom {

}
