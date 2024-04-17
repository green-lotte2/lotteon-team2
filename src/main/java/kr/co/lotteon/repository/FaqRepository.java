package kr.co.lotteon.repository;

import kr.co.lotteon.entity.FaqEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, Integer> {

    public Page<FaqEntity> findAll(Pageable pageable);
    public Page<FaqEntity> findByTitleContains(String keyword, Pageable pageable);
    public Page<FaqEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<FaqEntity> findByCate1(int cate1, Pageable pageable);

}