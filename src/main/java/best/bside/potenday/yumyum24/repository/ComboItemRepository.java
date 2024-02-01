package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.ComboItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboItemRepository extends JpaRepository<ComboItem, Long>, ComboItemRepositoryCustom {

}
