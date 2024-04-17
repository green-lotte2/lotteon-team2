package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CsCate2;
import kr.co.lotteon.entity.CsCate2;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface Cate2Repository extends JpaRepository<CsCate2, Integer> {

    @Query("SELECT a FROM CsCate2 a " + "WHERE a.csCateKey.cate1 = :cate1 ")
    List<CsCate2> findAllByCate1(@Param("cate1") int cate1);

}
