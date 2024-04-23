package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    //noticeList
    List<NoticeDTO> selectNoticeListAll(int start);
    List<NoticeDTO> selectNoticeListCate(int cate1, int start);

    //notice
    public int selectNoticeTotal();
    public int selectNoticeTotalCate(int cate1);

    public NoticeDTO selectNoticeView(int noticeno);

    //🎈admin notice view
    public NoticeDTO adminSelectNoticeView(int noticeno);

    //🎈 admin notice 수정
    public NoticeDTO adminSelectNoticeBoard(int noticeno);
    public void adminUpdateNoticeBoard(NoticeDTO dto);

    //🎈 admin notice 삭제
    public void adminDeleteNoticeBoard(int noticeno);
}
