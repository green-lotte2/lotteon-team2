package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_Pno(int pno);
    List<Review> findByUid(String uid);

}
