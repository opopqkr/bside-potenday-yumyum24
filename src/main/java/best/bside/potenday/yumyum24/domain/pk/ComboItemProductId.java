package best.bside.potenday.yumyum24.domain.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ComboItemProductId implements Serializable {

    private Long comboItemId;

    private Long productId;

}
