package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom {

}
