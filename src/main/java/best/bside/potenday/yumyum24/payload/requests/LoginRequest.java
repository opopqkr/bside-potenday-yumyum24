package best.bside.potenday.yumyum24.payload.requests;

import best.bside.potenday.yumyum24.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String picture;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build();
    }
}
