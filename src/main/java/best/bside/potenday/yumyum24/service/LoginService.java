package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.config.jwt.JwtProvider;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.payload.requests.LoginRequest;
import best.bside.potenday.yumyum24.payload.responses.LoginResponse;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        // 없는 사용자일 경우 save.
        if (user == null) {
            user = loginRequest.toEntity();
            userRepository.save(user);
        } else {
            if (StringUtils.isNotBlank(user.getPicture()) || StringUtils.equals(user.getPicture(), loginRequest.getPicture())) {
                user.setPicture(loginRequest.getPicture());
                userRepository.save(user);
            }
        }

        return LoginResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .token(jwtProvider.createToken(user.getEmail()))
                .build();
    }
}
