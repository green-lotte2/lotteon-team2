package kr.co.lotteon.controller.admin;


import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.CsQna;
import kr.co.lotteon.entity.Reply;
import kr.co.lotteon.service.CsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class AdminCsController {

    @Autowired
    private CsService csService;




    /////////////////////////
    // 🎈공지사항 //////////////
    ////////////////////////
    @GetMapping("/admin/cs/notice/list")
    public String adminNoticeList(@RequestParam(name="pg", defaultValue = "1") String pg,
                                  @RequestParam(name="cate1", required = false) String cate1,
                                  Model model){

        log.info("pg : " + pg);
        log.info("cate1 : " + cate1);

        // 현재 페이지 번호
        int currentPage = csService.getCurrentPage(pg);
        log.info("currentPage : " + currentPage);

        // 시작 인덱스
        int start = csService.getStartNum(currentPage);
        log.info("start : " + start);

        // 전체 게시물 갯수
        int total;
        List<NoticeDTO> noticeDTOS;


        if(cate1 == null || cate1.isEmpty()){
            log.info("notice1");
            total = csService.selectNoticeTotal();
            noticeDTOS = csService.selectNoticeListAll(start);
        }else {
            log.info("notice2");
            log.info("notice2 cate1 : " + cate1);
            total = csService.selectNoticeTotalCate(Integer.parseInt(cate1));

            log.info("notice2 total : " + total);

            noticeDTOS = csService.selectNoticeListCate(Integer.parseInt(cate1), start);
        }

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작 번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("noticeDTOS", noticeDTOS);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);
        model.addAttribute("cate1", cate1);
        log.info("currentPage: " + currentPage);
        log.info("lastPage: " + lastPageNum);
        log.info("cate1last : " + cate1);

        return "/admin/cs/notice/list";

    }

    //🎈 공지사항 view
    @GetMapping("/admin/cs/notice/view")
    public String adminNoticeView(int noticeno, Model model){
        NoticeDTO noticeBoard = csService.adminSelectNoticeView(noticeno);
        model.addAttribute("noticeBoard", noticeBoard);
        log.info("noticeno : " + noticeno);
        log.info("noticeBoard : " + noticeBoard.toString());
        return "/admin/cs/notice/view";
    }


    // 🎈공지사항 글등록
    @GetMapping("/admin/cs/notice/write")
    public String adminNoticeWrite(){
        return "/admin/cs/notice/write";
    }

    @PostMapping("/admin/cs/notice/write")
    public String adminNoticeWrite(HttpServletRequest req, @RequestParam("cate1") int cate1, NoticeDTO noticeDTO, Model model){
        String regip = req.getRemoteAddr();
        noticeDTO.setRegip(regip);
        noticeDTO.setCate1(cate1);
        noticeDTO.setCate2(cate1);
        //📢 로그인 설정, 조회수 변경하기//
        noticeDTO.setUid("hello");
        noticeDTO.setHit(noticeDTO.getHit());
        csService.adminInsertNotice(noticeDTO);
        model.addAttribute("noticeDTO", noticeDTO);
        log.info("notie글등록 : " + noticeDTO);
        return "redirect:/admin/cs/notice/list?noticeno="+noticeDTO.getNoticeno();
    }


    //🎈 공지사항 수정
    @GetMapping("/admin/cs/notice/modify")
    public String adminNoticeModify(int noticeno, Model model){
        NoticeDTO noticeBoard = csService.adminSelectNoticeBoard(noticeno);
        model.addAttribute("noticeBoard", noticeBoard);
        log.info("noticeno: " + noticeno);
        log.info("noticboard: " + noticeBoard);
        return "/admin/cs/notice/modify";
    }

    @PostMapping("/admin/cs/notice/modify")
    public String adminNoticeUpdate(@ModelAttribute NoticeDTO dto){
        dto.setRdate(LocalDateTime.now());
        csService.adminUpdateNoticeBoard(dto);
        log.info("updateNoticeBoardDTO--------" + dto);
        int noticeno = dto.getNoticeno();
        log.info("updateNoticeBoardNoticeno---------"+ noticeno);
        return "redirect:/admin/cs/notice/view?noticeno="+noticeno;
    }

    //🎈 공지사항 삭제
    @GetMapping("/admin/cs/notice/delete")
    public String adminDeleteNoticeBoard(int noticeno) {
        csService.adminDeleteNoticeBoard(noticeno);
        log.info("noticeno : " + noticeno);

        return "redirect:/admin/cs/notice/list?noticeno="+noticeno;
    }

    @PostMapping("/admin/cs/notice/delete")
    public String adminDeleteNoticeBoard(@RequestParam List<String> checkbox){
        for(String noticeno : checkbox){
            int noticeId = Integer.parseInt(noticeno);
            csService.adminDeleteNoticeBoard(noticeId);
            log.info("noticeno : " + noticeId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/cs/notice/list";
    }



    ////////////////////////
    // 🎈자주 묻는 질문 ////////
    ///////////////////////
    @GetMapping("/admin/cs/faq/list")
    public String adminFaqList(@RequestParam(name="pg", defaultValue = "1") String pg,
                               @RequestParam(name="cate1", required = false) String cate1,
                               Model model){

        log.info("pg : " + pg);
        log.info("cate1 : " + cate1);

        // 현재 페이지 번호
        int currentPage = csService.getCurrentPage(pg);
        log.info("currentPage : " + currentPage);

        // 시작 인덱스
        int start = csService.getStartNum(currentPage);
        log.info("start : " + start);

        // 전체 게시물 갯수
        int total;
        List<FaqDTO> faqDTOS;


        if(cate1 == null || cate1.isEmpty()){
            log.info("notice1");
            total = csService.selectFaqTotal();
            faqDTOS = csService.selectFaqListAll(start);
        }else {
            log.info("faq2");
            log.info("faq2 cate1 : " + cate1);
            total = csService.selectFaqTotalCate(Integer.parseInt(cate1));

            log.info("notice2 total : " + total);

            faqDTOS = csService.selectFaqListCate(Integer.parseInt(cate1), start);
        }

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작 번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("faqDTOS", faqDTOS);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);
        model.addAttribute("cate1", cate1);
        log.info("currentPage: " + currentPage);
        log.info("lastPage: " + lastPageNum);
        log.info("cate1last : " + cate1);

        return "/admin/cs/faq/list";
    }



    //🎈 자주묻는질문 view
    @GetMapping("/admin/cs/faq/view")
    public String adminFaqView(int faqno, Model model){

        FaqDTO faqBoard = csService.adminSelectFaqView(faqno);
        model.addAttribute("faqBoard",faqBoard);

        log.info("faqno : " + faqno);
        log.info("faqBoard : " + faqBoard);

        return "/admin/cs/faq/view";
    }

    // 🎈자주묻는질문 글등록
    @GetMapping("/admin/cs/faq/write")
    public String adminFaqWrite(){
        return "/admin/cs/faq/write";
    }

    @PostMapping("/admin/cs/faq/write")
    public String adminFaqWrite(HttpServletRequest req, FaqDTO faqDTO){
        String regip = req.getRemoteAddr();
        faqDTO.setRegip(regip);
        //📢 로그인 설정, 조회수 변경하기//
        faqDTO.setUid("hello");
        faqDTO.setHit(faqDTO.getHit());
        csService.adminInsertFaq(faqDTO);
        return "redirect:/admin/cs/faq/list?faqno="+faqDTO.getFaqno();
    }

    //🎈 자주묻는질문 수정
    @GetMapping("/admin/cs/faq/modify")
    public String adminFaqModify(int faqno, Model model){
        FaqDTO faqBoard = csService.adminSelectFaqBoard(faqno);
        model.addAttribute("faqBoard", faqBoard);
        log.info("faqno: " + faqno);
        log.info("faqboard: " + faqBoard);
        return "/admin/cs/faq/modify";
    }

    @PostMapping("/admin/cs/faq/modify")
    public String adminFaqUpdate(@ModelAttribute FaqDTO dto){
        dto.setRdate(LocalDateTime.now());
        csService.adminUpdateFaqBoard(dto);
        log.info("updateFaqBoardDTO--------" + dto);
        int faqno = dto.getFaqno();
        log.info("updateFaqBoardFaqno---------"+ faqno);
        return "redirect:/admin/cs/faq/view?faqno="+faqno;
    }




    //🎈 자주묻는질문 삭제
    @GetMapping("/admin/cs/faq/delete")
    public String adminDeleteFaqBoard(int faqno) {
        csService.adminDeleteFaqBoard(faqno);
        log.info("faqno : " + faqno);

        return "redirect:/admin/cs/faq/list?faqno="+faqno;
    }


    @PostMapping("/admin/cs/faq/delete")
    public String adminDeleteFaqBoard(@RequestParam List<String> checkbox){
        for(String faqno : checkbox){
            int faqId = Integer.parseInt(faqno);
            csService.adminDeleteFaqBoard(faqId);
            log.info("deleteFaqno : " + faqId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/cs/faq/list";
    }


    /////////////////////////
    // 🎈1:1 질문 /////////////
    ////////////////////////
    @GetMapping("/admin/cs/qna/list")
    public String adminQnaList(@RequestParam(name="pg", defaultValue = "1") String pg,
                               @RequestParam(name="cate1", required = false) String cate1,
                               Model model){
        log.info("pg : " + pg);
        log.info("cate1 : " + cate1);

        // 현재 페이지 번호
        int currentPage = csService.getCurrentPage(pg);
        log.info("currentPage : " + currentPage);

        // 시작 인덱스
        int start = csService.getStartNum(currentPage);
        log.info("start : " + start);

        // 전체 게시물 갯수
        int total;
        List<QnaDTO> qnaDTOS;


        if(cate1 == null || cate1.isEmpty()){
            log.info("qna1");
            total = csService.selectQnaTotal();
            qnaDTOS = csService.selectQnaListAll(start);
        }else {
            log.info("qna2");
            log.info("qna2 cate1 : " + cate1);
            total = csService.selectQnaTotalCate(Integer.parseInt(cate1));

            log.info("qna2 total : " + total);

            qnaDTOS = csService.selectQnaListCate(Integer.parseInt(cate1), start);
        }

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작 번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("qnaDTOS", qnaDTOS);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);
        model.addAttribute("cate1", cate1);
        log.info("currentPage: " + currentPage);
        log.info("lastPage: " + lastPageNum);
        log.info("cate1last : " + cate1);

        return "/admin/cs/qna/list";
    }

    @GetMapping("/admin/cs/qna/view")
    public String adminQnaView(int qnano, int cate2, String c2name, String writer, Model model){
        QnaDTO qnaBoard = csService.adminSelectQnaView(qnano);
        model.addAttribute("cate2", cate2);
        model.addAttribute("qnaBoard", qnaBoard);
        model.addAttribute("c2name", c2name);
        model.addAttribute("writer", writer);
        log.info("cate2 : " + cate2);
        log.info("qnano : " + qnano);
        log.info("qnaBoard : " + qnaBoard);
        log.info("writer : " + writer);

        return "/admin/cs/qna/view";
    }

    @GetMapping("/admin/cs/qna/modify")
    public String adminQnaModify(Integer qnano, Model model){
        QnaDTO qnaBoard = csService.adminSelectQnaBoard(qnano);
        model.addAttribute("qnaBoard", qnaBoard);
        log.info("qnano : " + qnano);
        log.info("qnaBoard : " + qnaBoard);

        return "/admin/cs/qna/modify";
    }

    @PostMapping("/admin/cs/qna/modify")
    public String adminUpdateQnaBoard(@ModelAttribute QnaDTO dto){
        dto.setRdate(LocalDateTime.now());
        csService.adminUpdateQnaBoard(dto);
        log.info("updateQnaBoardDTO------" + dto);
        int qnano = dto.getQnano();
        log.info("updateQnaBoardQnano------------"+qnano);
        return "redirect:/admin/cs/qna/view?qnano="+qnano;
    }

    //🎈 1:1문의 삭제
    @GetMapping("/admin/cs/qna/delete")
    public String adminDeleteQnaBoard(int qnano) {
        csService.adminDeleteQnaBoard(qnano);
        log.info("qnano : " + qnano);

        return "redirect:/admin/cs/qna/list?qnano="+qnano;
    }

    @PostMapping("/admin/cs/qna/delete")
    public String adminDeleteQnaBoard(@RequestParam List<String> checkbox){
        for(String qnano : checkbox){
            int qnaId = Integer.parseInt(qnano);
            csService.adminDeleteQnaBoard(qnaId);
            log.info("deleteQnano : " + qnaId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/cs/qna/list";
    }



    //🎈1:1질문 답변//

    // 🎈답변 목록
    @ResponseBody
    @GetMapping("/admin/cs/qna/reply/{qnano}")
    public ResponseEntity<List<ReplyDTO>> reply(@PathVariable("qnano") int qnano){

        log.info("replyQnano : " + qnano);
        return  csService.selectReplies(qnano);
    }

    //🎈 답변 등록
    @ResponseBody
    @PostMapping("/admin/cs/qna/reply")
    public ResponseEntity<Reply> reply(@RequestBody ReplyDTO replyDTO){

        return csService.insertReply(replyDTO);
    }

    // 🎈답변 삭제
    @DeleteMapping("/admin/cs/qna/reply/{qnano}")
    public ResponseEntity<?> deleteComment(@PathVariable("qnano") int qnano) {

        return csService.deleteReply(qnano);
    }

    @PutMapping("/admin/cs/qna/reply")
    public ResponseEntity<?> putReply(@RequestBody ReplyDTO replyDTO) {
        return csService.updateReply(replyDTO);
    }

}
