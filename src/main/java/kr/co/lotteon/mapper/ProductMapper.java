package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    public List<UserDTO> SelectUsers();

    public List<ProductDTO> selectProducts();

    // 상품 최신순 8개
    public List<ProductDTO> selectProductsByNewest();

    // 상품 조회수순 8개
    public List<ProductDTO> selectProductsByHit();

    // 상품 주문량순 8개
    public List<ProductDTO> selectProductsByOrder();

    // 상품 할인률순 8개
    public List<ProductDTO> selectProductsByDiscount();

    // 상품 평점순 8개
    public List<ProductDTO> selectProductsByScore();


    List<ProductDTO> selectCartWithProductsByUid(String uid);

    ProductDTO selectProductWithImagesById(int pno);

    List<ProductDTO> searchProducts(String search, Integer minPrice, Integer maxPrice, int cate);

    int countSearchProducts(String search, Integer minPrice, Integer maxPrice, int cate);
}
