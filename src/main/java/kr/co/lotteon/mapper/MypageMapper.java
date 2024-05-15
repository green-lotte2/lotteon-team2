package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MypageMapper {
/*
    public List<QnaDTO> selectCsQnaCommentView(int qnano);
*/
    public List<QnaDTO> selectMyQnaBoard(String uid, int start);

    public String selectMyQnaTotal(String uid);

    List<ReviewDTO> selectReviews(@Param("uid") String uid, @Param("limit") int limit);

    public String selectC2Name(int cate2);
    void insertReview(ReviewDTO reviewDTO);
}
