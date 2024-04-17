package kr.co.lotteon.service;

import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.entity.UserDetail;
import kr.co.lotteon.repository.UserDetailRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public void insertUser(UserDTO userDTO){
        
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


}
