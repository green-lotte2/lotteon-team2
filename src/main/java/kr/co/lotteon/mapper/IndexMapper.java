package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexMapper {

    public List<NoticeDTO> selectNotices();
    public List<QnaDTO> selectQna();
}
