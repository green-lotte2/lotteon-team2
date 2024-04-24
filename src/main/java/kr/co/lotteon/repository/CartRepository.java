package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByUid(String uid);
    List<Cart>  findCartByUidAndPno(String uid, int pno);

    @Transactional
    void deleteByUidAndPno(String uid, int pno);
}
