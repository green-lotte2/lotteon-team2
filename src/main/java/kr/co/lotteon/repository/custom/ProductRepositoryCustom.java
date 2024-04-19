package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    public Page<Tuple> selectProducts(PageRequestDTO pageRequestDTO, Pageable pageable);
    public Page<Tuple> searchProducts(PageRequestDTO pageRequestDTO, Pageable pageable);

}
