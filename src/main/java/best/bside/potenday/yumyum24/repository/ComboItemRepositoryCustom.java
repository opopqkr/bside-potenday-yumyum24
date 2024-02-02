package best.bside.potenday.yumyum24.repository;

import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;

import java.util.List;

public interface ComboItemRepositoryCustom {

    ComboItemInfo findByCategory(Category category);

    List<ComboItemInfo> findOrderByRandom();

}
