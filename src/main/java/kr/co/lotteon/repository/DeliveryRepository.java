package kr.co.lotteon.repository;

import kr.co.lotteon.dto.DeliveryDTO;
import kr.co.lotteon.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT new kr.co.lotteon.dto.DeliveryDTO(o.ono, od.pno, p.pname, od.pcount, o.odate, o.uid, d.state, d.stname) " +
            "FROM Delivery d " +
            "JOIN Orders o ON d.state = o.state " +
            "JOIN OrderDetail od ON o.ono = od.ono " +
            "JOIN Product p ON od.pno = p.pno " +
            "ORDER BY o.odate DESC ")
    Page<DeliveryDTO> findDeliveryList(Pageable pageable);


}
