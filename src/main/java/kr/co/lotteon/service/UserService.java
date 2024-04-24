package kr.co.lotteon.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.entity.UserDetail;
import kr.co.lotteon.mapper.UserMapper;
import kr.co.lotteon.repository.UserDetailRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void insertUser(UserDTO userDTO) {

        // 비밀번호 암호화
        userDTO.setPass(passwordEncoder.encode(userDTO.getPass()));

        // 기본권한
        userDTO.setGrade(1);

        User user = modelMapper.map(userDTO, User.class);
        UserDetail userDetail = modelMapper.map(userDTO, UserDetail.class);

        log.info(user);
        log.info(userDetail);

        userRepository.save(user);
        userDetailRepository.save(userDetail);


    }

    public void updateUserPoint(String uid, int point, int savepoint){
        Optional<UserDetail> optUserDetail = userDetailRepository.findById(uid);
        if(optUserDetail.isPresent()){
            UserDetail userDetail = optUserDetail.get();
            int result = userDetail.getPoint() - point + savepoint;
            userDetail.setPoint(result);

            userDetailRepository.save(userDetail);
        }
    }

    public boolean existsById(String uid) {
        return userRepository.existsByUid(uid);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByHp(String hp) {
        return userRepository.existsByHp(hp);
    }

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmailCode(HttpSession session, String receiver){

        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        session.setAttribute("code", code);

        log.info("code : " + code);

        String title = "lotteon 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";


        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("error={}", e.getMessage());
        }

    }

    public User selectUser(String uid){
        Optional<User> optUser = userRepository.findById(uid);
        if(optUser.isPresent()){
            return optUser.get();
        }
        return null;
    }

    public UserDTO selectUserDetail(String uid){
        log.info(userMapper.selectUser(uid));
        return userMapper.selectUser(uid);
    }

    public String getUid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        }
        return null;
    }

}