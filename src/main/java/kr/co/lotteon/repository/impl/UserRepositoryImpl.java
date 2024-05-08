package kr.co.lotteon.repository.impl;


import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    public void adminSelectUsers(String uid, PageRequestDTO pageRequestDTO){

    }

}
