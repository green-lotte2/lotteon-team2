package kr.co.lotteon.repository;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.repository.custom.OrderRepositoryCustom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer>, OrderRepositoryCustom {
    @Query("SELECT r FROM Orders r WHERE r.odate BETWEEN :beginDate AND :endDate")
    List<Orders> findRecordsBetween(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    Page<Orders> findAllByUid(String uid, Pageable pageable);

    List<Orders> findTop3ByUidOrderByOdateDesc(String uid);


}
