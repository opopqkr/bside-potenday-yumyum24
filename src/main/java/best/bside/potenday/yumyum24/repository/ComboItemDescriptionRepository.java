package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.domain.pk.ComboItemDescriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ComboItemDescriptionRepository extends JpaRepository<ComboItemDescription, ComboItemDescriptionId> {

    List<ComboItemDescription> findByComboItemIdOrderByOrderNumber(Long id);

}
