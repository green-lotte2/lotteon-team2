package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CsCate1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cate1Repository extends JpaRepository<CsCate1, Integer> {

    @Query("SELECT a FROM CsCate1 a " + "WHERE a.cate1 < 20 ")
    List<CsCate1> findCate1sForNotice();

    @Query("SELECT a FROM CsCate1 a " + "WHERE a.cate1 >= 20 ")
    List<CsCate1> findCate1sForQna();
}
