package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.config.jwt.JwtProvider;
import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.payload.requests.LoginRequest;
import best.bside.potenday.yumyum24.payload.responses.Profile;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    public String login(LoginRequest loginRequest) {
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

        return jwtProvider.createToken(user.getEmail());
    }

    public Profile getProfile() {
        String email = validationToken();

        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. 관리자에게 문의해 주세요.");

        return Profile.builder()
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .build();
    }

    public String validationToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
