package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import kr.co.lotteon.dto.UserDTO;


@Mapper
public interface AdminMapper{

    public List<UserDTO> adminSelectUsers();

    //🎈상품
    // admin 상품목록
    public List<ProductDTO> adminSelectProducts();
    // admin 상품삭제
    public void adminDeleteProduct(int pno);

    
    //🎈공지사항//
    //  목록
    public List<NoticeDTO> adminNoticeList(int start);
    //  view
    public NoticeDTO adminNoticeView(int noticeno);
    //  수정
    public void adminNoticeUpdate(NoticeDTO dto);



    //🎈자주묻는질문//
    // 목록
    public List<FaqDTO> adminFaqList(int cate1);
    // view
    public FaqDTO adminFaqView(int faqno);




}
