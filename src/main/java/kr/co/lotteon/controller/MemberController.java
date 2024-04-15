package kr.co.lotteon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("member")
@Controller
public class MemberController {

    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }

    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/member/register";
    }

    @GetMapping("/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }

    @GetMapping("/signup")
    public String signup(){
        return "/member/signup";
    }




}
