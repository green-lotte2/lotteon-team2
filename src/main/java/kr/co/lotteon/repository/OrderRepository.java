package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT r FROM Orders r WHERE r.odate BETWEEN :beginDate AND :endDate")
    List<Orders> findRecordsBetween(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
