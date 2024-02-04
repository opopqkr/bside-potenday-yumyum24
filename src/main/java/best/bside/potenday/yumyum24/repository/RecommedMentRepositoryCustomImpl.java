package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QRecommendMent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RecommedMentRepositoryCustomImpl extends BaseRepository implements RecommedMentRepositoryCustom {

    private final static DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String findRecommedMentByNow(LocalTime now) {
        QRecommendMent m = QRecommendMent.recommendMent;

        return select(m.ment)
                .from(m)
                .where(m.startTime.loe(LocalTime.parse(LOCAL_TIME_FORMATTER.format(now)))
                        .and(m.endTime.gt(LocalTime.parse(LOCAL_TIME_FORMATTER.format(now)))))
                .fetchOne();
    }
}
