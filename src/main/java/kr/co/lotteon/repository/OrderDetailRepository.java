package kr.co.lotteon.repository;

import kr.co.lotteon.dto.OrderDetailDTO;
import kr.co.lotteon.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOno(int ono);

    //🎈주문 정보 리스트
    @Query("SELECT new kr.co.lotteon.dto.OrderDetailDTO(o.ono, od.pno, p.pname, od.pcount, o.odate, o.uid, od.state) " +
            "From OrderDetail od " +
            "JOIN Orders o ON o.ono = od.ono " +
            "JOIN Product p ON od.pno = p.pno " +
            "ORDER BY o.odate DESC ")
    Page<OrderDetailDTO> findDeliveryList(Pageable pageable);


    // 🎈배송상태 변경
    OrderDetail findByOnoAndPno(int ono, int pno);

    // 🎈주문 번호(ono)와 제품 번호(pno)를 찾아 상태(state)를 변경하는 메소드
    default OrderDetail updateStateByOnoAndPno(int ono, int pno, String state) {
        OrderDetail orderDetail = findByOnoAndPno(ono, pno);
        orderDetail.setState(state);
        save(orderDetail);{

        }
         return orderDetail;
    }


}
