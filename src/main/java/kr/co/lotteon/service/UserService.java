package kr.co.lotteon.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.entity.UserDetail;
import kr.co.lotteon.mapper.UserMapper;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.UserDetailRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
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

    @Transactional
    public void insertSeller(UserDTO userDTO) {

        // 비밀번호 암호화
        userDTO.setPass(passwordEncoder.encode(userDTO.getPass()));

        // 기본권한
        userDTO.setGrade(1);
        userDTO.setRole("SELLER");
        User user = modelMapper.map(userDTO, User.class);
        Seller seller = modelMapper.map(userDTO, Seller.class);

        log.info(user);
        log.info(seller);

        userRepository.save(user);
        sellerRepository.save(seller);


    }

    public void updateUserPoint(String uid, int point, int savepoint){
        Optional<UserDetail> optUserDetail = userDetailRepository.findById(uid);
        if(optUserDetail.isPresent()){
            UserDetail userDetail = optUserDetail.get();
            int result = userDetail.getPoint() - point + savepoint;
            log.info(userDetail.getPoint() + "원래포인트");
            log.info(point + "사용포인트");
            log.info(savepoint + "적립포인트");
            log.info(result + "최종포인트");
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

    public boolean existsByCohp(String cohp){
        return sellerRepository.existsByCohp(cohp);
    }

    public boolean existsByRegnum(String regnum){
        return sellerRepository.existsByRegnum(regnum);
    }

    public boolean existsByReportnum(String reportnum){
        return sellerRepository.existsByReportnum(reportnum);
    }

    public boolean existsByFax(String fax){
        return sellerRepository.existsByFax(fax);
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

    public UserDTO selectSeller(String uid){
        log.info(userMapper.selectSeller(uid));
        return userMapper.selectSeller(uid);
    }

    public String getUid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        }
        return null;
    }

    public UserDTO findByNameAndEmail(String name, String email){
        log.info("name :" + name);
        log.info("email :" + email);
        Optional<User> optUser = userRepository.findUserByNameAndEmail(name, email);
        UserDTO userDTO = null;

        log.info("findUser..." + optUser);

        if (optUser.isPresent()){
            User user = optUser.get();
            userDTO = modelMapper.map(user, UserDTO.class);

            return userDTO;
        }else {
            return null; // 또는 예외를 던지거나 다른 방식으로 처리
        }
    }

    public UserDTO findPassword(String uid, String email){
        Optional<User> optUser = userRepository.findUserByUidAndEmail(uid, email);
        UserDTO userDTO = null;

        log.info("findpass..." + optUser);

        if (optUser.isPresent()){
            User user = optUser.get();
            userDTO = modelMapper.map(user, UserDTO.class);


            return userDTO;
        }else {
            return null; // 또는 예외를 던지거나 다른 방식으로 처리
        }
    }

    public ResponseEntity<?> updateUserPass(UserDTO userDTO){
        Optional<User> optUser = userRepository.findById(userDTO.getUid());

        if (optUser.isPresent()){
            User user = optUser.get();

            String encoded = passwordEncoder.encode(userDTO.getPass());
            log.info(encoded);
            user.setPass(encoded);


            log.info("updateUser....."+ user);

            User updateUser = userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updateUser);
        }else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }

    // 비밀번호 찾기를 통한 비밀번호 재설정
    public void updatePass(String uid, String pass) {
        pass = passwordEncoder.encode(pass);
        User entity = userRepository.findById(uid).get();
        entity.setPass(pass);
        userRepository.save(entity);
    }

    // 회원 탈퇴
    public void updateWdate(String uid) {
        User entity = userRepository.findById(uid).get();
        entity.setLeaveDate(LocalDateTime.now());
        userRepository.save(entity);
    }

    // 회원정보 수정 (이메일, 전화번호, 주소)
    public void updateUser(UserDTO userDTO) {
        User entity = userRepository.findById(userDTO.getUid()).get();
        entity.setEmail(userDTO.getEmail());
        entity.setHp(userDTO.getHp());
        entity.setZip(userDTO.getZip());
        entity.setAddr1(userDTO.getAddr1());
        entity.setAddr2(userDTO.getAddr2());

        Seller entitySeller = sellerRepository.findById(userDTO.getUid()).get();
        entitySeller.setCompany(userDTO.getCompany());
        entitySeller.setRepresent(userDTO.getRepresent());
        entitySeller.setRegnum(userDTO.getRegnum());
        entitySeller.setReportnum(userDTO.getReportnum());
        entitySeller.setCohp(userDTO.getCohp());
        entitySeller.setFax(userDTO.getFax());

        userRepository.save(entity);
        sellerRepository.save(entitySeller);
    }
}