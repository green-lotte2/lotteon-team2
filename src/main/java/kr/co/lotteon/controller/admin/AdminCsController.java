package kr.co.lotteon.controller.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminCsController {

    @GetMapping("/admin/cs/faq/list")
    public String adminFaqList(){
        return "/admin/cs/faq/list";
    }

    @GetMapping("/admin/cs/faq/view")
    public String adminFaqView(){
        return "/admin/cs/faq/view";
    }

    @GetMapping("/admin/cs/faq/write")
    public String adminFaqWrite(){
        return "/admin/cs/faq/write";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String adminFaqModify(){
        return "/admin/cs/faq/modify";
    }

    @GetMapping("/admin/cs/notice/list")
    public String adminNoticeList(){
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/view")
    public String adminNoticeView(){
        return "/admin/cs/notice/view";
    }

    @GetMapping("/admin/cs/notice/write")
    public String adminNoticeWrite(){
        return "/admin/cs/notice/write";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String adminNoticeModify(){
        return "/admin/cs/notice/modify";
    }

    @GetMapping("/admin/cs/qna/list")
    public String adminQnaList(){
        return "/admin/cs/qna/list";
    }

    @GetMapping("/admin/cs/qna/view")
    public String adminQnaView(){
        return "/admin/cs/qna/view";
    }

    @GetMapping("/admin/cs/qna/reply")
    public String adminQnaReply(){
        return "/admin/cs/qna/reply";
    }


}































