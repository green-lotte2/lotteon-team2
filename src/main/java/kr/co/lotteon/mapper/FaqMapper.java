package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.dto.FaqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqMapper {

    List<FaqDTO> selectFaqList10(int cate1);

    public FaqDTO selectFaqView(int faqno);

    //🎈 admin faq 목록
    List<FaqDTO> selectFaqList();

    // 🎈 admin faq view
    public FaqDTO adminSelectFaqView(int faqno);

}
