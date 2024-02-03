package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ReplyInfo;

public interface ReplyRepositoryCustom {

    Page<ReplyInfo> findByPageInfo(Long comboItemId, PageInfo pageInfo);

    Page<ReplyInfo> findByPageInfo(Long writerId, Long comboItemId, PageInfo pageInfo);

}
