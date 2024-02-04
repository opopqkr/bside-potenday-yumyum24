package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QRecommendMent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RecommedMentRepositoryCustomImpl extends BaseRepository implements RecommedMentRepositoryCustom {

    @Override
    public String findRecommedMentByNow(LocalTime now) {
        QRecommendMent m = QRecommendMent.recommendMent;

        return select(m.ment)
                .from(m)
                .where(m.startTime.goe(LocalTime.parse(DateTimeFormatter.ofPattern("HH:mm").format(now)))
                        .and(m.endTime.loe(LocalTime.parse(DateTimeFormatter.ofPattern("HH:mm").format(now)))))
                .fetchOne();
    }
}
