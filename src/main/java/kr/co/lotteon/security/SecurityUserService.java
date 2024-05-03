package kr.co.lotteon.security;

import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findById(username);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        }

        UserDetails userDetails = null;

        if (!result.isEmpty()) {
            // 해당하는 사용자가 존재하면 인증 객체 생성
            userDetails = MyUserDetails.builder()
                    .user(result.get())
                    .build();
        }
        if (user != null){
            if (user.getLeaveDate() != null) {
                throw new UsernameNotFoundException("탈퇴한 회원입니다.");
            }
        }
        // Security ContextHolder 저장
        return userDetails;
    }
}
