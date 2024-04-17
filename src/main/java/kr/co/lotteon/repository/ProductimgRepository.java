package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Productimg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductimgRepository extends JpaRepository<Productimg, Integer> {
}
