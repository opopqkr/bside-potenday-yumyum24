package best.bside.potenday.yumyum24.payload.requests;

import best.bside.potenday.yumyum24.domain.Reply;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReplyRequest {

    @NotBlank
    @Max(value = 255, message = "255 글자 이상 작성할 수 없습니다.")
    private String content;

    public Reply toEntity(Long comboItemId, String userName) {
        return Reply.builder()
                .comboItemId(comboItemId)
                .userName(userName)
                .content(content)
                .build();

    }
}
