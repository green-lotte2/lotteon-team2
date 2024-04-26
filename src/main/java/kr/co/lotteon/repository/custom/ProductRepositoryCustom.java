package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    public Page<Tuple> selectProducts(PageRequestDTO pageRequestDTO, Pageable pageable);

    Page<Tuple> searchProducts(PageRequestDTO pageRequestDTO, Pageable pageable);
}
