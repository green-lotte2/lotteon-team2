package kr.co.lotteon.repository;

import kr.co.lotteon.entity.QnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {

    public Page<QnaEntity> findAll(Pageable pageable);
    public Page<QnaEntity> findByTitleContains(String keyword, Pageable pageable);
    public Page<QnaEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<QnaEntity> findByCate1(int cate1, Pageable pageable);

    public List<QnaEntity> findByParent(int parent);

    int countByUid(String uid);

    List<QnaEntity> findTop3ByUidOrderByRdate(String uid);

}
