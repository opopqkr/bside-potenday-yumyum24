package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.domain.pk.BookmarkId;
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
@IdClass(BookmarkId.class)
@Table(name = "BOOKMARK")
public class Bookmark {

    @Id
    private Long userId;

    @Id
    private Long comboItemId;

    private String comboItemName;

    @Convert(converter = CategoryConverter.class)
    private Category category;
    // 찜 여부
    private String isBookmarked;

    private LocalDateTime savedAt;
    public void completeSaveBookmark() {
        this.savedAt = LocalDateTime.now();
    }
}
