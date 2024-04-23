package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {

    public List<QnaDTO> selectCsQnaComment(int qnano);

    public List<QnaDTO> selectMyQnaBoard(String uid, int start);

    public String selectMyQnaTotal(String uid);
}
