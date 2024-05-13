package kr.co.lotteon.service;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.CsFaq;
import kr.co.lotteon.entity.CsNotice;
import kr.co.lotteon.entity.CsQna;
import kr.co.lotteon.entity.Reply;
import kr.co.lotteon.mapper.*;
import kr.co.lotteon.repository.FaqRepository;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.repository.QnaRepository;
import kr.co.lotteon.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;
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


    // 🎈faq 게시판 총 갯수 카운트
    public int selectFaqTotal(){
        return faqMapper.selectFaqTotal();
    }

    // 🎈faq 게시판 cate로 참고한 총 갯수 카운트
    public int selectFaqTotalCate(int cate1){
        return faqMapper.selectFaqTotalCate(cate1);
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

    // noticeList cate 참조
    public List<FaqDTO> selectFaqListCate (int cate1, int start){
        return faqMapper.selectFaqListCate(cate1, start);
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
        return qnaMapper.adminSelectQnaView(qnano);
    }
    public FaqDTO selectFaqView(int faqno){
        return faqMapper.selectFaqView(faqno);
    }


    // CsFaq 리스트
    public List<FaqDTO> selectFaqList10(int cate1){
        return faqMapper.selectFaqList10(cate1);
    }
    public List<FaqDTO> selectFaqListAll (int start){
        return faqMapper.selectFaqListAll(start);
    }

    //////////////////////////////////////////
    ///////////////🎀Admin🎀/////////////////
    /////////////////////////////////////////
    ////////////////////////////////////////

    //✨공지사항✨//

    // 🎈Admin Notice 글등록
    public void adminInsertNotice(NoticeDTO noticeDTO){

        // noticeDTO를 noticeEntity로 변환
        CsNotice notice = modelMapper.map(noticeDTO, CsNotice.class);
        log.info(notice.toString());

        CsNotice savedNoticeBoard = noticeRepository.save(notice);
        log.info("insertNotice : " + savedNoticeBoard);
    }

    //🎈 공지사항 리스트
    public List<NoticeDTO> noticeList(){

        return noticeRepository.findAll()
                .stream()
                .map(
                        CsNotice::toDTO
                )
                .collect(Collectors.toList());
    }


    // 🎈공지사항 리스트 페이징
    public PageResponseDTO noticeList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("noticeno");
        Page<CsNotice> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = noticeRepository.findAll(pageable);
            }else{
                result = noticeRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = noticeRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = noticeRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<NoticeDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsNotice::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .noticeList(dtoList)
                .total(totalElement)
                .build();
    }



    // 🎈Admin 공지사항 view
    public NoticeDTO adminSelectNoticeView(int noticeno){
        return noticeMapper.adminSelectNoticeView(noticeno);
    }

    // 🎈 Admin 공지사항 수정
    public NoticeDTO adminSelectNoticeBoard(int noticeno){
        log.info("noticeno" + noticeno);
        return noticeMapper.adminSelectNoticeBoard(noticeno);
    }

    public void adminUpdateNoticeBoard(NoticeDTO dto) {
        noticeMapper.adminUpdateNoticeBoard(dto);
    }

    // 🎈 Admin 공지사항 삭제
    public void adminDeleteNoticeBoard(int noticeno) {
        noticeMapper.adminDeleteNoticeBoard(noticeno);
    }

    
    
    //✨자주묻는질문✨//

    // 🎈Admin 자주묻는질문 글등록
    public void adminInsertFaq(FaqDTO faqDTO){

        // faqDTO를 faqEntity로 변환
        CsFaq faq = modelMapper.map(faqDTO, CsFaq.class);
        log.info(faq.toString());

        CsFaq savedFaqBoard = faqRepository.save(faq);
        log.info("insertFaq : " + savedFaqBoard);
    }


    // 🎈Admin 자주묻는질문 리스트

    public List<FaqDTO> selectFaqList(){

        return faqRepository.findAll()
                .stream()
                .map(
                        CsFaq::toDTO
                )
                .collect(Collectors.toList());
    }

    // 🎈Admin 자주묻는질문 리스트 카테고리
    public List<Cate2DTO> adminSelectCate2(){
        return cateMapper.adminSelectCate2();
    }

    // 🎈Admin 자주묻는질문 view
    public FaqDTO adminSelectFaqView(int faqno){
        return faqMapper.adminSelectFaqView(faqno);
    }

    // 🎈Admin 자주묻는질문 수정
    public FaqDTO adminSelectFaqBoard(int faqno){
        log.info("faqno : " + faqno);
        return faqMapper.adminSelectFaqBoard(faqno);
    }

    public void adminUpdateFaqBoard(FaqDTO dto){
        faqMapper.adminUpdateFaqBoard(dto);
    }

    // 🎈 Admin 자주묻는질문 삭제
    public void adminDeleteFaqBoard(int faqno) {
        faqMapper.adminDeleteFaqBoard(faqno);
    }

    // FAQ 리스트
    public PageResponseDTO faqList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("faqno");
        Page<CsFaq> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = faqRepository.findAll(pageable);
            }else{
                result = faqRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = faqRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = faqRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<FaqDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsFaq::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .faqList(dtoList)
                .total(totalElement)
                .build();
    }





    //✨1:1 문의✨//

    // 🎈 Admin Qna 리스트
    public List<QnaDTO> adminSelectQnaList(){

        return  qnaMapper.adminSelectQnaList();
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


    // Qna 페이지 리스트
    public PageResponseDTO qnaList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("qnano");
        Page<CsQna> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = qnaRepository.findAll(pageable);
            }else{
                result = qnaRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = qnaRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = qnaRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<QnaDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsQna::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .qnaList(dtoList)
                .total(totalElement)
                .build();
    }



    // 🎈 Admin Qna 뷰 코멘트
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

/*
    // myqna 답변
    public List<QnaDTO> selectCsQnaCommentView(int qnano){
        return mypageMapper.selectCsQnaCommentView(qnano);
    }
 */

    // 🎈Qna 답변 등록
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<Reply> insertReply(ReplyDTO replyDTO) {

        Optional<CsQna> optQna = qnaRepository.findById(replyDTO.getQnano());
        if(optQna.isPresent()){
            CsQna qna = optQna.get();
            if(qna.getAnswercomplete() != 2){
                qna.setAnswercomplete(2);
                qnaRepository.save(qna);
            }

        }
        Reply reply = modelMapper.map(replyDTO,Reply.class);
        Reply savedQna = replyRepository.save(reply);
        log.info("savedQna : " + savedQna);

        return ResponseEntity.ok().body(savedQna);
    }


    // 🎈 Qna 답변 목록

    public ResponseEntity<List<ReplyDTO>> selectReplies(int qnano){

        // ArticleRepository > findByParent() 쿼리 메서드 정의
        List<Reply> replyList = replyRepository.findByQnano(qnano);

        List<ReplyDTO> replyDTOS = replyList.stream()
                .map(entity -> modelMapper.map(entity, ReplyDTO.class))
                .toList();
        return ResponseEntity.ok().body(replyDTOS);
    }


    // 🎈 Qna 답변 수정
    public ResponseEntity<?> updateReply(ReplyDTO replyDTO){
        // 수정하기 전에 먼저 존재여부 확인
        Optional<Reply> optArticle = replyRepository.findById(replyDTO.getReplyno());

        if(optArticle.isPresent()) {

            Reply reply = optArticle.get();
            // 어쩔수 없이 Article 엔티티에 @Setter 선언해서 수정하기
            reply.setRcontent(replyDTO.getRcontent());

            log.info("reply : " + reply);

            Reply modifiedReply = replyRepository.save(reply);

            // 수정 후 데이터 반환
            return ResponseEntity.status(HttpStatus.OK).body(modifiedReply);
        }else{
            // 사용자가 존재하지 않으면 NOT_FOUND 응답데이터와 user not found 메세지
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }


    // 🎈 Qna 답변 삭제
    public ResponseEntity<?> deleteReply(int qnano){

        log.info("replyno : " + qnano);

        // 삭제 전 조회
        Optional<Reply> optReply = replyRepository.findById(qnano);

        log.info("optReply : " + optReply);

        if(optReply.isPresent()){
            log.info("here1");
            // 삭제된 답변의 Answercomplete 값을 다시 1로 설정
            Optional<CsQna> optQna = qnaRepository.findById(optReply.get().getQnano());
            optQna.ifPresent(qna -> {
                qna.setAnswercomplete(1);
                qnaRepository.save(qna);
            });

            replyRepository.deleteById(qnano);

            return ResponseEntity
                    .ok()
                    .body(optReply.get());
        } else {
            log.info("here2");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }


}
