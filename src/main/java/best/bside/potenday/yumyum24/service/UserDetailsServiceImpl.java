package best.bside.potenday.yumyum24.service;

import best.bside.potenday.yumyum24.domain.User;
import best.bside.potenday.yumyum24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. 관리자에게 문의해 주세요.");

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
