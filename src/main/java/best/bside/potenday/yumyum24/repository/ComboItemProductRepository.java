package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.ComboItemProduct;
import best.bside.potenday.yumyum24.domain.pk.ComboItemProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboItemProductRepository extends JpaRepository<ComboItemProduct, ComboItemProductId>, ComboItemProductRepositoryCustom {

}
