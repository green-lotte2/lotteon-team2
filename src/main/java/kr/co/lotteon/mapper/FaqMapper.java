package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqMapper {

    List<FaqDTO> selectFaqList10(int cate1);
    //🎈 admin faq 리스트 페이징🎈
    List<FaqDTO> selectFaqListAll(int start);
    List<FaqDTO> selectFaqListCate(int cate1, int start);

    public int selectFaqTotal();
    public int selectFaqTotalCate(int cate1);

    public FaqDTO selectFaqView(int faqno);

    //🎈 admin faq 목록
    List<FaqDTO> selectFaqList();

    // 🎈 admin faq view
    public FaqDTO adminSelectFaqView(int faqno);

    // 🎈 admin faq 수정
    public FaqDTO adminSelectFaqBoard(int faqno);
    public void adminUpdateFaqBoard(FaqDTO dto);


    // 🎈 admin faq 삭제
    public void adminDeleteFaqBoard(int faqno);

}
