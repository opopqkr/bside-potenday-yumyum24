package best.bside.potenday.yumyum24.payload.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Page<T> {

    List<T> result;

    PageInfo pageInfo;

}
