package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cate1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cate1Repository extends JpaRepository<Cate1Entity, Integer> {

    @Query("SELECT a FROM Cate1Entity a " + "WHERE a.cate1 < 20 ")
    List<Cate1Entity> findCate1sForNotice();

    @Query("SELECT a FROM Cate1Entity a " + "WHERE a.cate1 >= 20 ")
    List<Cate1Entity> findCate1sForQna();
}
