package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.domain.Reply;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;

public interface ReplyRepositoryCustom {

    Page<Reply> findByPageInfo(Long comboItemId, PageInfo pageInfo);

}
