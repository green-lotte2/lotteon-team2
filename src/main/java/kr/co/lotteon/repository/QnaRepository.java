package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CsQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<CsQna, Integer> {

    public Page<CsQna> findAll(Pageable pageable);
    public Page<CsQna> findByTitleContains(String keyword, Pageable pageable);
    public Page<CsQna> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<CsQna> findByCate1(int cate1, Pageable pageable);

    public List<CsQna> findByQnano(int qnano);
    public CsQna findFirstByQnano(int qnano);

    int countByUid(String uid);

    List<CsQna> findTop3ByUidOrderByRdate(String uid);

}
