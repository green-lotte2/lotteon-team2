package kr.co.lotteon.oauth2;


import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.UserRepository;
import kr.co.lotteon.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {



        String accessToken = userRequest.getAccessToken().getTokenValue();


        String provider = userRequest.getClientRegistration().getRegistrationId();


        OAuth2User oauth2User = super.loadUser(userRequest);


        Map<String, Object> attributes = oauth2User.getAttributes();


        /*
        if(provider.equals("kakao")){
        }else if(provider.equals("google")){
        }*/

        // 사용자 확인 및 회원가입 처리

        String email = (String) attributes.get("email");
        String uid = email.substring(0, email.lastIndexOf("@"));
        String name = (String) attributes.get("name");

        // 사용자 확인
        User user = userRepository.findById(uid)
                .orElse(User.builder()
                        .uid(uid)
                        .email(email)
                        .name(name)
                        .role("USER")
                        .build());

        userRepository.save(user);


        return MyUserDetails.builder()
                .user(user)
                .build();
    }
}