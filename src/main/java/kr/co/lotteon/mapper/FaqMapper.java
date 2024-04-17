package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.FaqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqMapper {

    List<FaqDTO> selectFaqList10(int cate1);
    public FaqDTO selectFaqView(int faqno);

}
