package best.bside.potenday.yumyum24.payload.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.ValidationException;

@Getter
public class PageInfo {

    private final int page;

    private final int size;

    @Setter
    private Long totalItemCount;

    public PageInfo(int page, int size) {
        this.page = page <= 0 ? 1 : page;
        this.size = size;
    }

    public int getOffset() {
        if (page < 1) {
            throw new ValidationException("유효하지 않은 페이지 입니다.");
        }
        return (page - 1) * size;
    }

    public int getTotalPage() {
        totalItemCount = totalItemCount == null ? 0 : totalItemCount;
        return Math.max(1, (int) Math.ceil((totalItemCount * 1.0) / (size * 1.0)));
    }
}
