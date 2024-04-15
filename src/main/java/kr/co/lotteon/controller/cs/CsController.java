package kr.co.lotteon.controller.cs;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class CsController {

    // 인덱스
    @GetMapping(value = {"/cs", "/cs/index"})
    public String index(){
        return "/cs/index";
    }



    // 자주 묻는 질문
    @GetMapping("/cs/faq/list")
    public String faqList() {
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView() {
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
