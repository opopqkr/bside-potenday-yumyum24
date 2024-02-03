package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QReply;
import best.bside.potenday.yumyum24.domain.QUser;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ReplyInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;

import java.util.List;

public class ReplyRepositoryCustomImpl extends BaseRepository implements ReplyRepositoryCustom {

    @Override
    public Page<ReplyInfo> findByPageInfo(Long comboItemId, PageInfo pageInfo) {
        QReply r = QReply.reply;
        QUser u = QUser.user;

        final List<ReplyInfo> list =
                select(Projections.constructor(ReplyInfo.class,
                        r.replyId,
                        u.name.as("writerName"),
                        u.email.as("writerEmail"),
                        Expressions.asBoolean(false).as("isEditable"),
                        r.content,
                        r.issuedAt))
                        .from(r)
                        .where(r.comboItemId.eq(comboItemId))
                        .leftJoin(u)
                        .on(r.writerId.eq(u.userId))
                        .offset(pageInfo.getOffset())
                        .limit(pageInfo.getSize())
                        .orderBy(r.issuedAt.desc())
                        .fetch();

        pageInfo.setTotalItemCount(select(r.count()).from(r).where(r.comboItemId.eq(comboItemId)).fetchOne());
        return new Page<>(list, pageInfo);
    }

    @Override
    public Page<ReplyInfo> findByPageInfo(Long writerId, Long comboItemId, PageInfo pageInfo) {
        QReply r = QReply.reply;
        QUser u = QUser.user;

        final List<ReplyInfo> list =
                select(Projections.constructor(ReplyInfo.class,
                        r.replyId,
                        u.name.as("writerName"),
                        u.email.as("writerEmail"),
                        new CaseBuilder()
                                .when(r.writerId.eq(writerId))
                                .then(true)
                                .otherwise(false)
                                .as("isEditable"),
                        r.content,
                        r.issuedAt))
                        .from(r)
                        .where(r.comboItemId.eq(comboItemId))
                        .leftJoin(u)
                        .on(r.writerId.eq(u.userId))
                        .offset(pageInfo.getOffset())
                        .limit(pageInfo.getSize())
                        .orderBy(r.issuedAt.desc())
                        .fetch();

        pageInfo.setTotalItemCount(select(r.count()).from(r).where(r.comboItemId.eq(comboItemId)).fetchOne());
        return new Page<>(list, pageInfo);
    }
}
