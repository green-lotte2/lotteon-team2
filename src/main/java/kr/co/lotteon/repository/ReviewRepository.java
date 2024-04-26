package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByUid(String uid);

}
