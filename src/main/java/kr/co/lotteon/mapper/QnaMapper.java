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

    public QnaDTO selectQnaView(int qnano);
    public QnaDTO selectAdminQnaView(int qnano);

    public QnaDTO selectQnaBoard(int qnano);
    public void updateQnaBoard(QnaDTO dto);
    public void deleteQnaBoard(int qnano);
    public QnaDTO selectQnaChildBoard(int qnano);

}
