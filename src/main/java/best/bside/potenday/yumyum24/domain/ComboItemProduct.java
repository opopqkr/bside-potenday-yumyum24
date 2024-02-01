package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.domain.pk.ComboItemProductId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ComboItemProductId.class)
@Table(name = "COMBO_ITEM_PRODUCT")
public class ComboItemProduct implements Serializable {

    @Id
    private Long comboItemId;

    @Id
    private Long productId;

}
