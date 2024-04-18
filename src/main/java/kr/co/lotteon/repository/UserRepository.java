package kr.co.lotteon.repository;

import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    int countByUid(String uid);
    int countByHp(String hp);
    int countByEmail(String email);
}
