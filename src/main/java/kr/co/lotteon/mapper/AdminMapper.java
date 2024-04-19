package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import kr.co.lotteon.dto.UserDTO;


@Mapper
public interface AdminMapper{

    public List<UserDTO> adminSelectUsers();

    // admin 상품목록
    public List<ProductDTO> adminSelectProducts();

    // admin 상품삭제
    public void adminDeleteProduct(int pno);

    // cs modify용
    public void updateAdminNotice(NoticeDTO dto);

}
