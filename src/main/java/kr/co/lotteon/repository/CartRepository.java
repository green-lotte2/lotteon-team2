package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByUid(String uid);
}
