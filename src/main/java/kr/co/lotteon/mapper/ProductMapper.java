package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    public List<UserDTO> SelectUsers();

    public List<ProductDTO> selectProducts();

    public List<ProductDTO> selectProductsForNew();

    List<ProductDTO> selectCartWithProductsByUid(String uid);

    ProductDTO selectProductWithImagesById(int pno);

    List<ProductDTO> searchProducts(String search, Integer minPrice, Integer maxPrice, String category);

    int countSearchProducts(String search, Integer minPrice, Integer maxPrice, String category);
}
