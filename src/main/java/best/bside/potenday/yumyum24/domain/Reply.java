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
@Table(name = "REPLY")
public class Reply {

    @Id
    @GeneratedValue
    private Long replyId;

    @Column(nullable = false)
    private Long comboItemId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String content;

    private LocalDateTime issuedAt;

    private LocalDateTime modifiedAt;

    public void completeIssueReply() {
        this.issuedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateReply() {
        this.modifiedAt = LocalDateTime.now();
    }
}
