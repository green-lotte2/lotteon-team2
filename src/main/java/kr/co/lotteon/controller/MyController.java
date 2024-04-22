package kr.co.lotteon.controller;

import kr.co.lotteon.entity.User;
import kr.co.lotteon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
@Slf4j
@Controller
@RequiredArgsConstructor
public class MyController {

    private final UserService userService;

    @GetMapping(value = {"/mypage/","/mypage/index"})
    public String myPage(){
        return "/my/home";
    }

    @GetMapping("/mypage/order")
    public String order(){
        return "/my/order";
    }

    @GetMapping("/mypage/point")
    public String point(){
        return "/my/point";
    }

    @GetMapping("/mypage/coupon")
    public String coupon(){
        return "/my/coupon";
    }

    @GetMapping("/mypage/review")
    public String review(){
        return "/my/review";
    }

    @GetMapping("/mypage/qna")
    public String qna(){
        return "/my/qna";
    }

    @GetMapping("/mypage/info")
    public String info(Principal principal, Model model){
        log.info(principal.toString());
        log.info(principal.getName());
        User user = userService.selectUser(principal.getName());
        model.addAttribute("user", user);
        return "/my/info";
    }

}