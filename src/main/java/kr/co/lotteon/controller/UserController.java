package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.service.TermsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("member")
@Controller
@AllArgsConstructor
public class UserController {

    private final TermsService termsService;

    //약관페이지
    @GetMapping("/terms")
    public String terms(Model model, String type){

        TermsDTO termsResult = termsService.selectTerms();

        model.addAttribute("type", type);
        model.addAttribute("termsResult", termsResult);

        return "/member/terms";
    }

    //회원 가입 페이지
    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }

    //일반회원 가입 폼페이지
    @GetMapping("/register")
    public String registerForm(){
        return "/member/register";
    }

    //판매자회원 가입 폼페이지
    @GetMapping("/registerSeller")
    public String registerSellerForm(){
        return "/member/registerSeller";
    }

    //회원가입 처리
    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        // 약관 동의 여부 확인
        boolean agree1 = Boolean.parseBoolean(request.getParameter("agree1"));
        boolean agree2 = Boolean.parseBoolean(request.getParameter("agree2"));
        boolean agree3 = Boolean.parseBoolean(request.getParameter("agree3"));

        // 필수 약관 모두 동의한 경우에만 가입 처리
        if (agree1 && agree2 && agree3) {
            // 회원 가입 처리 로직 작성
            // ...

            // 가입이 완료되면 로그인 페이지로 리다이렉트
            return "redirect:/member/login";
        } else {
            // 필수 약관 중 하나라도 동의하지 않은 경우 다시 회원 가입 페이지로 이동
            return "redirect:/member/register";
        }
    }


    // 판매자 회원 가입 처리
    @PostMapping("/registerSeller")
    public String registerSeller(HttpServletRequest request) {
        // 약관 동의 여부 확인
        boolean agree1 = Boolean.parseBoolean(request.getParameter("agree1"));
        boolean agree2 = Boolean.parseBoolean(request.getParameter("agree2"));
        boolean agree3 = Boolean.parseBoolean(request.getParameter("agree3"));

        // 필수 약관 모두 동의한 경우에만 가입 처리
        if (agree1 && agree2 && agree3) {
            // 판매자 회원 가입 처리 로직 작성
            // ...

            // 가입이 완료되면 로그인 페이지로 리다이렉트
            return "redirect:/member/login";
        } else {
            // 필수 약관 중 하나라도 동의하지 않은 경우 다시 회원 가입 페이지로 이동
            return "redirect:/member/registerSeller";
        }
    }
}