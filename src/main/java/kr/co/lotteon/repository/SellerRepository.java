package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {

    boolean existsByCohp(String cohp);
    boolean existsByRegnum(String regnum);
    boolean existsByReportnum(String reportnum);
    boolean existsByFax(String fax);

}
