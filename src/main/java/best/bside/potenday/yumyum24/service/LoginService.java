package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.config.jwt.JwtProvider;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.payload.requests.LoginRequest;
import best.bside.potenday.yumyum24.payload.responses.LoginResponse;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        // FIXME 수정 필요,
        // 없는 사용자일 경우 save.
        if (user == null) {
            userRepository.save(loginRequest.toEntity());
        } else if (!user.getPicture().equals(loginRequest.getPicture())) {
            // 이미지 정보 변경 시 update.
            user.setPicture(loginRequest.getPicture());
            userRepository.save(user);
        }

        return LoginResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .token(jwtProvider.createToken(user.getEmail()))
                .build();
    }
}
