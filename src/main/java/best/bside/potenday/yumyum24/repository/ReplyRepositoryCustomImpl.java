package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.QReply;
import best.bside.potenday.yumyum24.domain.Reply;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;

import java.util.List;

public class ReplyRepositoryCustomImpl extends BaseRepository implements ReplyRepositoryCustom {

    @Override
    public Page<Reply> findByPageInfo(Long comboItemId, PageInfo pageInfo) {
        QReply r = QReply.reply;

        final List<Reply> list = selectFrom(r).where(r.comboItemId.eq(comboItemId))
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .orderBy(r.issuedAt.desc())
                .fetch();

        pageInfo.setTotalItemCount(select(r.count()).from(r).where(r.comboItemId.eq(comboItemId)).fetchOne());
        return new Page<>(list, pageInfo);
    }
}
