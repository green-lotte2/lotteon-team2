package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {

    public void insertQnaWrite(QnaDTO dto);

    List<QnaDTO> selectQnaListAll(int start);
    List<QnaDTO> selectQnaListCate(int cate1 , int start);

    public int selectQnaTotalCate(int cate1);
    public int selectQnaTotal();



    public QnaDTO selectQnaBoard(int qnano);
    public void updateQnaBoard(QnaDTO dto);
    public void deleteQnaBoard(int qnano);
    public QnaDTO selectQnaChildBoard(int qnano);
    
    //🎈 admin 문의하기 리스트
    List<QnaDTO> adminSelectQnaList();

    //🎈 admin 문의하기 view
    public QnaDTO adminSelectQnaView(int qnano);

    //🎈 admin 문의하기 수정
    public QnaDTO adminSelectQnaBoard(int qnano);
    public void adminUpdateQnaBoard(QnaDTO dto);

    //🎈 admin 문의하기 삭제
    public void adminDeleteQnaBoard(int qnano);

}
