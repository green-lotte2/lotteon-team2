package kr.co.lotteon.controller.cs;

import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.lotteon.service.CsService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class CsController {

    @Autowired
    private CsService csService;

    ////////////////////////
    // 인덱스 ///////////////
    ///////////////////////
    @GetMapping(value = {"/cs", "/cs/index"})
    public String index(Model model){

        List<NoticeDTO> noticelist = csService.selectNotices();
        model.addAttribute("noticelist", noticelist);

        List<QnaDTO> qnalist = csService.selectQna();
        model.addAttribute("qnalist", qnalist);

        log.info(noticelist.toString());
        log.info(qnalist.toString());

        return "/cs/index";
    }



    ////////////////////////
    // 자주 묻는 질문 ////////
    ///////////////////////
    @RequestMapping("/cs/faq/list")
    public String faq(@RequestParam(name= "cate1", required = false) int cate1, Model model){
        log.info("faqcate1 : " + cate1);
        List<FaqDTO> faqDTOList = csService.selectFaqList10(cate1);
        List<Cate2DTO> cate2list = csService.selectCate2(cate1);
        model.addAttribute("cate2list",cate2list);
        model.addAttribute("faqDTOList",faqDTOList);
        model.addAttribute("cate1",cate1);
        log.info("faqDTOList : " + faqDTOList);
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String selectFaqView(int faqno, Model model){
        FaqDTO faqBoard = csService.adminSelectFaqView(faqno);
        model.addAttribute("faqBoard",faqBoard);

        log.info("faqno : " + faqno);
        return "/cs/faq/view";
    }



    /////////////////////////
    // 공지사항 //////////////
    ////////////////////////
    @GetMapping("/cs/notice/list")
    public String selectNoticeList(@RequestParam(name="pg", defaultValue = "1") String pg,
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
        log.info(currentPage);
        log.info(lastPageNum);
        log.info("cate1list : " + cate1);

        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String selectNoticeView(int noticeno, Model model){
        NoticeDTO noticeBoard = csService.selectNoticeView(noticeno);
        model.addAttribute("noticeBoard", noticeBoard);
        log.info("noticeno : " + noticeno);
        log.info("noticeBoard : " + noticeBoard.toString());
        return "/cs/notice/view";
    }



    /////////////////////////
    // 1:1 질문 /////////////
    ////////////////////////

    @RequestMapping("/cs/qna/list")
    public String selectQnaListAll(@RequestParam(name="pg", defaultValue = "1") String pg,
                                   @RequestParam(name="cate1", required = false) String cate1,
                                   Model model) {

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
            log.info("qnaDTOS 1 : " + total);
            qnaDTOS = csService.selectQnaListAll(start);
            log.info("qnaDTOS 2 : " + qnaDTOS);
        }else {
            log.info("qna2");
            log.info("qna2 cate1 : " + cate1);
            total = csService.selectQnaTotalCate(Integer.parseInt(cate1));

            log.info("qna2 total : " + total);

            qnaDTOS = csService.selectQnaListCate(Integer.parseInt(cate1), start);
        }

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);
        log.info(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);
        log.info(lastPageNum);

        // 페이지 시작 번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("qnaDTOS",qnaDTOS);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("lastPageNum",lastPageNum);
        model.addAttribute("pageGroupStart",result[0]);
        model.addAttribute("pageGroupEnd",result[1]);
        model.addAttribute("pageStartNum",pageStartNum+1);
        model.addAttribute("cate1",cate1);
        log.info(currentPage);
        log.info(lastPageNum);
        log.info("cate1list : " + cate1);

        return "/cs/qna/list";
    }



    @GetMapping("/cs/qna/view")
    public String selectQnaView(int qnano, Model model) {
        log.info(qnano);
        QnaDTO qnaBoard = csService.selectQnaView(qnano);
        log.info("qnaBoard : " + qnaBoard);

        model.addAttribute("qnaBoard", qnaBoard);
        model.addAttribute("c2name", qnaBoard.getC2name());

        if (qnaBoard == null) {
            // qnaboard가 null인 경우에 대한 처리
            return "/cs/qna/list";
        }
        model.addAttribute("qnaBoard", qnaBoard);
        model.addAttribute("c2name", qnaBoard.getC2name());

        QnaDTO qnaChildBoard = csService.selectQnaChildBoard(qnano);
        model.addAttribute("qnaChildBoard", qnaChildBoard);

        if (qnaChildBoard == null) {
            return "/cs/qna/view";
        }

        log.info("qnano : " + qnano);
        log.info("qnaBoard : " + qnaBoard.toString());
        log.info("qnaChildBoard : " + qnaChildBoard);

        return "/cs/qna/view";
    }

    // Qna 글쓰기 화면
    @GetMapping("/cs/qna/write")
    public String writeView() {
        return "/cs/qna/write";
    }

    // Qna 글쓰기
    @PostMapping("/cs/qna/write")
    public String write(HttpServletRequest request, QnaDTO dto){
        dto.setRegip(request.getRemoteAddr());
        dto.setRdate(LocalDateTime.now());
        log.info(dto);
        csService.insertQnaWrite(dto);
        return "redirect:/cs/qna/list";
    }

    // Qna 파일 다운로드
    @GetMapping("/cs/qna/download")
    public void filedownload(int qnano, HttpServletResponse response) throws IOException{
        // dto 객체로부터 파일 경로를 가져옴
        QnaDTO dto = csService.selectQnaView(qnano);
        log.info("QnaDownload : " + dto);
        String filePath = dto.getFile1();
        String oName = dto.getFile2();

        String path = "uploads/" + filePath;
        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(oName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("/cs/qna/modify")
    public String selectQnaBoard(int qnano, Model model){
        QnaDTO qnaBoard = csService.selectQnaBoard(qnano);
        model.addAttribute("qnaBoard", qnaBoard);
        log.info("qnano : " + qnano);
        log.info("qnaBoard : " + qnaBoard.toString());
        return "/cs/qna/modify";
    }

    @PostMapping("/cs/qna/modify")
    public String updateQnaBoard (@ModelAttribute QnaDTO dto){
        dto.setRdate(LocalDateTime.now());
        csService.updateQnaBoard(dto);
        log.info("updateQnaBoardDTO------" + dto.toString());
        int qnano = dto.getQnano();
        log.info("updateQnaBoardQnano----------"+qnano);
        return "redirect:/cs/qna/view?qnano="+qnano;
    }

    @GetMapping("/cs/qna/delete")
    public String deleteQnaBoard (int qnano){

        // 게시물 삭제 전 파일 정보 가져오기
        QnaDTO dto = csService.selectQnaBoard(qnano);

        // 파일 경로
        String filePath = dto.getFile1();

        // 파일 삭제
        boolean isFileDeleted = deleteFile(filePath);
        csService.deleteQnaBoard(qnano);

        // 만약 파일이 정상적으로 삭제되었다면 게시물을 삭제한다.
        if(isFileDeleted){
            csService.deleteQnaBoard(qnano);
        }
        return "redirect:/cs/qna/list";
    }

    // 파일삭제 구현
    private boolean deleteFile(String filePath){
        try{
            File fileToDelete = new File("uploads/" + filePath);
            return fileToDelete.delete();
        }catch(Exception e){
            log.error("파일 삭제 중 오류 발생 : " + e.getMessage());
            return false;
        }
    }
}