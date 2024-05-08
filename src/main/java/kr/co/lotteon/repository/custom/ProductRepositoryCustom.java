package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductRepositoryCustom {

    Page<Tuple> selectProducts(PageRequestDTO productPageRequestDTO, Pageable pageable);

    Page<Tuple> searchProducts(PageRequestDTO pageRequestDTO, Pageable pageable);
}
