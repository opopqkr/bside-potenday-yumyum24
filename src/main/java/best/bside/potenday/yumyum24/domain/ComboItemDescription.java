package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.domain.pk.ComboItemDescriptionId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ComboItemDescriptionId.class)
@Table(name = "COMBO_ITEM_DESCRIPTION")
public class ComboItemDescription {

    @Id
    private Long comboItemId;

    @Id
    private int orderNumber;

    private String description;

}
