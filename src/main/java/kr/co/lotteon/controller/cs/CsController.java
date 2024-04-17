package kr.co.lotteon.controller.cs;

import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.service.CsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // 인덱스
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



    // 자주 묻는 질문
    @RequestMapping("/cs/faq/list")
    public String faq(@RequestParam(name= "cate1", required = false) int cate1, Model model){
        log.info("faqcate1-----------------"+cate1);
        List<FaqDTO> faqDTOList = csService.selectFaqList10(cate1);
        List<Cate2DTO> cate2list = csService.selectCate2(cate1);
        model.addAttribute("cate2list",cate2list);
        model.addAttribute("faqDTOList",faqDTOList);
        model.addAttribute("cate1",cate1);
        log.info("faqDTOList==============="+faqDTOList);
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String selectFaqView(int faqno, Model model){
        FaqDTO faqBoard = csService.selectFaqView(faqno);
        model.addAttribute("faqBoard",faqBoard);

        log.info("faqno--------"+faqno);
        return "/cs/faq/view";
    }


    // 1:1 질문
    @GetMapping("/cs/qna/list")
    public String qnaList() {

        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView() {

        return "/cs/qna/view";
    }

    @GetMapping("/cs/qna/write")
    public String qnaWrite() {

        return "/cs/qna/write";
    }


    // 공지사항
    @GetMapping("/cs/notice/list")
    public String noticeList(){
        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(){
        return "/cs/notice/view";
    }
}
