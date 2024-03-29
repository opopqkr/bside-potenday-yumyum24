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
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String shopType;

    private String productType;

    private String name;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String imageUrl;

    private int price;

    private long usedCount;

    private LocalDateTime savedAt;

    public void completeSaveProduct() {
        this.savedAt = LocalDateTime.now();
    }

}
