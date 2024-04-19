package kr.co.lotteon.repository;

import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUid(String uid);
    boolean existsByEmail(String email);
    boolean existsByHp(String hp);


}
