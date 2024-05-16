package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface AdminMapper{

    //🎈회원
    public List<UserDTO> adminSelectUsers();
    // 회원 수정
    public UserDTO adminUserSelect(String uid);

    // 회원 삭제
    public void adminDeleteUser(String uid);


    //🎈주문
    public List<OrdersDTO> selectOrderByMonth();

    public List<OrdersDTO> selectCountAndCateName();

    public List<OrdersDTO> selectOrderByWeek();


    public List<OrdersDTO> selectOrderGroup(PageRequestDTO pageRequestDTO);

    public List<OrdersDTO> selectOrders(PageRequestDTO pageRequestDTO);

    //🎈상품
    // admin 상품목록
    public List<ProductDTO> adminSelectProducts();
    // admin 상품삭제
    public void adminDeleteProduct(int pno);

    public List<ProductDTO> selectProductsBySearch(PageRequestDTO pageRequestDTO);

    //🎈배송
    // 배송목록
   //public List<OrderDetailDTO> selectDeliveryList();



    //🎈배너
    // 목록
    public List<BannerDTO> selectBanner();

    // 삭제
    public void deleteBanner(int bno);

    public int selectRegUser();


}
