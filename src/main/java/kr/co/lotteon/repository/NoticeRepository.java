package kr.co.lotteon.repository;

import kr.co.lotteon.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {

    public Page<NoticeEntity> findAll(Pageable pageable);
    public Page<NoticeEntity> findByTitleContains(String keyword, Pageable pageable);
    public Page<NoticeEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<NoticeEntity> findByCate1(int cate1, Pageable pageable);

}
