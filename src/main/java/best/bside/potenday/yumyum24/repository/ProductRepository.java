package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

}
