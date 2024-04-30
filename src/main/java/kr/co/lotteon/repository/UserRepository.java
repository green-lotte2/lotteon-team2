package kr.co.lotteon.repository;

import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUid(String uid);
    boolean existsByEmail(String email);
    boolean existsByHp(String hp);

    // 이름과 이메일 찾기 (아이디 찾기용)
    public Optional<User> findUserByNameAndEmail(String name, String email);

    // 아이디와 이메일 찾기 (비밀번호 찾기용)
    public Optional<User> findUserByUidAndEmail(String uid, String email);
}
