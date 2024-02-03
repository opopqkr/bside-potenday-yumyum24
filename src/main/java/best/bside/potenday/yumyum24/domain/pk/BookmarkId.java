package best.bside.potenday.yumyum24.domain.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class BookmarkId implements Serializable {

    private Long userId;

    private Long comboItemId;
}
