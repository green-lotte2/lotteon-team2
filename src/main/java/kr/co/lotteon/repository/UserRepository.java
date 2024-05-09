package kr.co.lotteon.repository;

import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    boolean existsByUid(String uid);
    boolean existsByEmail(String email);
    boolean existsByHp(String hp);

    // 이름과 이메일 찾기 (아이디 찾기용)
    public Optional<User> findUserByNameAndEmail(String name, String email);
    // 아이디와 이메일 찾기 (비밀번호 찾기용)
    public Optional<User> findUserByUidAndEmail(String uid, String email);


    /*
     *   JPA 페이지네이션 처리를 위한 Page 타입으로 변환
     *   -Page 타입은 한 페이지에 포함된 엔티티 목록을 표현
     */

    //🎈관리자 회원 페이징
    public Page<User> findByRole(String role, Pageable pageable);

}
