package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String findNameByEmail(String email);

    User findByEmail(String email);

}
