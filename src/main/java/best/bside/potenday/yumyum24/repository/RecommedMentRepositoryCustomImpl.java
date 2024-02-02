package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QRecommendMent;

import java.time.LocalTime;

public class RecommedMentRepositoryCustomImpl extends BaseRepository implements RecommedMentRepositoryCustom {

    @Override
    public String findRecommedMentByNow(LocalTime now) {
        QRecommendMent m = QRecommendMent.recommendMent;

        return select(m.ment)
                .from(m)
                .where(m.startTime.after(now).and(m.endTime.before(now)))
                .fetchOne();
    }
}
