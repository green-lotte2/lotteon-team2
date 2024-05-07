package kr.co.lotteon.repository;

import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.entity.CsFaq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<CsFaq, Integer> {

    public Page<CsFaq> findAll(Pageable pageable);
    public Page<CsFaq> findByTitleContains(String keyword, Pageable pageable);
    public Page<CsFaq> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<CsFaq> findByCate1(int cate1, Pageable pageable);



}