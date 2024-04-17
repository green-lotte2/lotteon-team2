package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CsNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<CsNotice, Integer> {

    public Page<CsNotice> findAll(Pageable pageable);
    public Page<CsNotice> findByTitleContains(String keyword, Pageable pageable);
    public Page<CsNotice> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<CsNotice> findByCate1(int cate1, Pageable pageable);

}
