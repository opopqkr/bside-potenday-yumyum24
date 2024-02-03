package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.domain.pk.BookmarkId;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

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

    @Column(nullable = false, length = 1)
    private String isBookmarked;

    private LocalDateTime savedAt;

    private LocalDateTime modifiedAt;

    public void completeSaveBookmark() {
        this.isBookmarked = "Y";
        this.savedAt = LocalDateTime.now();
    }

    public void completeModifiedBookmark() {
        isBookmarked = StringUtils.equals("Y", isBookmarked) ? "N" : "Y";
        this.modifiedAt = LocalDateTime.now();
    }
}
