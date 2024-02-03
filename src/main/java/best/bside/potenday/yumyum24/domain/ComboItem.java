package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.enums.Category;
import best.bside.potenday.yumyum24.enums.converter.CategoryConverter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comboItemId;

    @Column(unique = true, nullable = false)
    private String name;

    private String review;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    private Long bookmarkCount = 0L;

    private LocalDateTime createdAt;

    public void completeCreateComboItem() {
        this.createdAt = LocalDateTime.now();
    }
}
