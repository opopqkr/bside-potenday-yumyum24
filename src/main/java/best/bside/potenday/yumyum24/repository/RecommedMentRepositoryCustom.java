package best.bside.potenday.yumyum24.repository;

import java.time.LocalTime;

public interface RecommedMentRepositoryCustom {

    String findRecommedMentByNow(LocalTime now);

}
