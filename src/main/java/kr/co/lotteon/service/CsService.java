package kr.co.lotteon.service;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.CsQna;
import kr.co.lotteon.mapper.*;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Service
public class CsService {

    private final IndexMapper indexMapper;
    private final CateMapper cateMapper;
    private final QnaMapper qnaMapper;
    private final NoticeMapper noticeMapper;
    private final FaqMapper faqMapper;
    private final MypageMapper mypageMapper;

    private final QnaRepository qnaRepository;

    public List<NoticeDTO> selectNotices(){
        return indexMapper.selectNotices();
    }

    public List<QnaDTO> selectQna(){
        return indexMapper.selectQna();
    }

    public List<Cate1DTO> selectCate1(){
        return cateMapper.selectCate1();
    }

    public List<Cate2DTO> selectCate2(int cate1){
        return cateMapper.selectCate2(cate1);
    }


    // 파일등록
    public void insertQnaWrite(QnaDTO dto){
        log.info("insertQnaWrite : " + dto);
        if(dto.getMFile1() != null && !dto.getMFile1().isEmpty()){
            List<String> saveNames = fileUpload(dto);
            // 파일을 선택한 경우에만 처리
            dto.setFile1(saveNames.get(0));
            dto.setFile2(saveNames.get(1));
        }

        qnaMapper.insertQnaWrite(dto);
    }

    // 파일 저장경로
    @Value("${file.upload.path}")
    private String filePath;

    // 파일 업로드
    public List<String> fileUpload(QnaDTO dto){

        // 파일 첨부 경로(절대 경로로 변환)
        String path = new File(filePath).getAbsolutePath();
        // 첨부파일 리스트
        List<MultipartFile> files = Arrays.asList(dto.getMFile1());

        // 저장된 파일명 리스트 초기화
        List<String> saveNames = new ArrayList<>();
        log.info(path);
        for(MultipartFile file:files){
            //파일명 변경
            String oName = file.getOriginalFilename();  // 업로드시 원래 이름
            String ext = oName.substring(oName.lastIndexOf(".")); // 확장자
            String sName = UUID.randomUUID().toString() + ext;  // 새로운 파일 이름
            saveNames.add(sName);
            saveNames.add(oName);
            try{
                file.transferTo(new File(path, sName)); // 저장할 폴더, 파일 이름
            } catch (IOException e){
                log.error(e.getMessage());
            }
        }
        return saveNames;
    }



    ////////////////////////
    // 페이징 시작 //////////
    ///////////////////////
    //현재 페이지 번호
    public int getCurrentPage(String pg){
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }

        return currentPage;
    }

    // notice게시판 총 갯수 카운트
    public int selectNoticeTotal(){
        return noticeMapper.selectNoticeTotal();
    }

    // notice 게시판 cate로 참고한 총 갯수 카운트
    public int selectNoticeTotalCate(int cate1){
        return noticeMapper.selectNoticeTotalCate(cate1);
    }


    // myqna 게시판 총 갯수 카운트
    public String selectMyQnaTotal(String uid) {
        return mypageMapper.selectMyQnaTotal(uid);
    }


    // Qna게시판 총 갯수 카운트
    public int selectQnaTotal(){
        return qnaMapper.selectQnaTotal();
    }
    public int selectQnaTotalCate(int cate1){
        return qnaMapper.selectQnaTotalCate(cate1);
    }

    // 페이지 마지막 번호
    public int getLastPageNum(int total){

        int lastPageNum = 0;

        if(total % 10 == 0){
            lastPageNum = total / 10;
        }else{
            lastPageNum = total / 10 + 1;
        }
        return lastPageNum;
    }

    // 페이지 그룹
    public int[] getPageGroupNum(int currentPage, int lastPageNum) {
        int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
        int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
        int pageGroupEnd = currentPageGroup * 10;

        if(pageGroupEnd > lastPageNum){
            pageGroupEnd = lastPageNum;
        }

        int[] result = {pageGroupStart, pageGroupEnd};

        return result;
    }

    // 페이지 시작번호
    public int getPageStartNum(int total, int currentPage) {
        int start = (currentPage - 1) * 10;
        return total - start;
    }

    // Limit 시작번호
    public int getStartNum(int currentPage){
        return (currentPage - 1) * 10;
    }
    ////////////////////////
    // 페이징 끝 ////////////
    ///////////////////////



    // noticeList 전체
    public List<NoticeDTO> selectNoticeListAll (int start){
        return noticeMapper.selectNoticeListAll(start);
    }

    // noticeList cate 참조
    public List<NoticeDTO> selectNoticeListCate (int cate1, int start){
        return noticeMapper.selectNoticeListCate(cate1, start);
    }

    // qnaList 전체

    public List<QnaDTO> selectQnaListAll(int start){
        return qnaMapper.selectQnaListAll(start);
    }

    // qnaList cate 참조
    public List<QnaDTO> selectQnaListCate(int cate1, int start){
        return qnaMapper.selectQnaListCate(cate1, start);
    }



    //myqna uid 참조
    public List<QnaDTO> selectMyQnaBoard(String uid , int start) {
        return mypageMapper.selectMyQnaBoard(uid, start);
    }



    // 게시판 뷰
    public NoticeDTO selectNoticeView(int noticeno){
        return noticeMapper.selectNoticeView(noticeno);
    }
    public QnaDTO selectQnaView(int qnano){
        return qnaMapper.selectQnaBoard(qnano);
    }
    public FaqDTO selectFaqView(int faqno){
        return faqMapper.selectFaqView(faqno);
    }


    // CsFaq 리스트
    public List<FaqDTO> selectFaqList10(int cate1){
        return faqMapper.selectFaqList10(cate1);
    }

    //////////////////////////////////////////
    ///////////////🎀Admin🎀/////////////////
    /////////////////////////////////////////
    ////////////////////////////////////////

    // 🎈Admin Notice view
    public NoticeDTO adminSelectNoticeView(int noticeno){
        return noticeMapper.adminSelectNoticeView(noticeno);
    }

    // 🎈 Admin Notice 수정
    public NoticeDTO adminSelectNoticeBoard(int noticeno){
        log.info("noticeno" + noticeno);
        return noticeMapper.adminSelectNoticeBoard(noticeno);
    }

    // 🎈Admin Faq 리스트
    public List<FaqDTO> selectFaqList(){
        return faqMapper.selectFaqList();
    }

    // 🎈Admin Faq 리스트 카테고리
    public List<Cate2DTO> adminSelectCate2(){
        return cateMapper.adminSelectCate2();
    }

    // 🎈 Admin Qna 리스트
    public List<QnaDTO> adminSelectQnaList(){
        return  qnaMapper.adminSelectQnaList();
    }


    // 🎈Admin Faq view
    public FaqDTO adminSelectFaqView(int faqno){
        return faqMapper.adminSelectFaqView(faqno);
    }


    // 🎈 Admin Qna 뷰
    public QnaDTO adminSelectQnaView(int qnano){
        return qnaMapper.adminSelectQnaView(qnano);
    }

    // 🎈 Admin Qna 수정
    public QnaDTO adminSelectQnaBoard(int qnano){
        log.info("qnano" + qnano);
        return qnaMapper.adminSelectQnaBoard(qnano);
    }
    public void adminUpdateQnaBoard(QnaDTO dto) {
        qnaMapper.adminUpdateQnaBoard(dto);
    }

    // 🎈 Admin Qna 삭제
    public void adminDeleteQnaBoard(int qnano){
        qnaMapper.adminDeleteQnaBoard(qnano);
    }


    // Admin Qna 뷰 코멘트
    public List<CsQna> selectComments(int qnano){
        return qnaRepository.findByQnano(qnano);
    }

    // Qna 수정
    public QnaDTO selectQnaBoard(int qnano){
        return qnaMapper.selectQnaBoard(qnano);
    }
    public void updateQnaBoard(QnaDTO dto){
        qnaMapper.updateQnaBoard(dto);
    }

    // Qna 삭제
    public void deleteQnaBoard(int qnano){
        qnaMapper.deleteQnaBoard(qnano);
    }

    // Qna 답변
    public QnaDTO selectQnaChildBoard(int qnano){
        return qnaMapper.selectQnaChildBoard(qnano);
    }


    // myqna 답변
    public List<QnaDTO> selectCsQnaComment(int qnano){
        return mypageMapper.selectCsQnaComment(qnano);
    }

}
