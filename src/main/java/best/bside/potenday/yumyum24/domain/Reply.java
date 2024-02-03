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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(nullable = false)
    private Long comboItemId;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String content;

    private LocalDateTime issuedAt;

    private LocalDateTime modifiedAt;

    public void completeIssueReply() {
        this.issuedAt = LocalDateTime.now();
    }

    public void updateReply() {
        this.modifiedAt = LocalDateTime.now();
    }
}
