package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ReplyInfo;
import com.sun.istack.Nullable;

public interface ReplyRepositoryCustom {

    Page<ReplyInfo> findByPageInfo(@Nullable Long writerId, Long comboItemId, PageInfo pageInfo);

}
