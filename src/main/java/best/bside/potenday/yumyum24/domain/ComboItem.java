package best.bside.potenday.yumyum24.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMBO_ITEM")
public class ComboItem {

    @Id
    @GeneratedValue
    private Long comboItemId;

    @Column(unique = true, nullable = false)
    private String name;

    private Long isGoodCount = 0L;

    private LocalDateTime createdAt;

    public void completeCreateComboItem() {
        this.createdAt = LocalDateTime.now();
    }

}
