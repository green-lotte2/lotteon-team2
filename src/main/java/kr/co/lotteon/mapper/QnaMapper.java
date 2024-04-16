package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {

    public void insertQnaWrite(QnaDTO dto);

    List<QnaDTO> selectQnaListAll(int start);
    List<QnaDTO> selectQnaListCate(int cate1 , int start);

    public int selectQnaTotalCate(int cate1);
    public int selectQnaTotal();

    public QnaDTO selectQnaView(int qnaNo);
    public QnaDTO selectAdminQnaView(int qnaNo);

    public QnaDTO selectQnaBoard(int qnaNO);
    public void updateQnaBoard(QnaDTO dto);
    public void deleteQnaBoard(int qnaNo);
    public QnaDTO selectQnaChildBoard(int qnaNo);

}
