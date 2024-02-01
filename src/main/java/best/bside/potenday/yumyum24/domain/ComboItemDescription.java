package best.bside.potenday.yumyum24.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMBO_ITEM_DESCRIPTION")
public class ComboItemDescription {

    @Id
    @Column(unique = true)
    private Long comboItemId;

    @Column(nullable = false)
    private int orderNumber;

    private String description;

}
