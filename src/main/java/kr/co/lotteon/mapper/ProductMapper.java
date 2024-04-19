package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductMapper {

    // admin 상품목록
    public List<ProductDTO> selectProductsForNew();


}
