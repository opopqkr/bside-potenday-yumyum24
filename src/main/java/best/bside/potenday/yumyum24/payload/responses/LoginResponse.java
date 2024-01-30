package best.bside.potenday.yumyum24.payload.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String name;

    private String email;

    private String picture;

    private String token;

}
