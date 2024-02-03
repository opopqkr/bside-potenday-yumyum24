package best.bside.potenday.yumyum24.domain;

import best.bside.potenday.yumyum24.domain.pk.BookmarkId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
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

    private LocalDateTime savedAt;

    public void completeSaveBookmark() {
        this.savedAt = LocalDateTime.now();
    }
}
