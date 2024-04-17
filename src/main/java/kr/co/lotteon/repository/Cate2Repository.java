package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cate2Entity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface Cate2Repository extends JpaRepository<Cate2Entity, Integer> {

    @Query("SELECT a FROM Cate2Entity a " + "WHERE a.cateKey.cate1 = :cate1 ")
    List<Cate2Entity> findAllByCate1(@Param("cate1") int cate1);

}
