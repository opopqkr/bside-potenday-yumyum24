package best.bside.potenday.yumyum24.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReplyInfo {

    private Long replyId;

    private String writerName;

    private String writerEmail;

    private Boolean isEditable;

    private String content;

    private LocalDateTime issuedAt;

}
