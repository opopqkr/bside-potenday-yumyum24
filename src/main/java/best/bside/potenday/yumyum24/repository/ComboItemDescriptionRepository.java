package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboItemDescriptionRepository extends JpaRepository<ComboItemDescription, Long> {

    List<ComboItemDescription> findByComboItemId(Long id);

}
