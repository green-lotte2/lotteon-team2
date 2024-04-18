package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyController {

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
    public String info(){
        return "/my/info";
    }

}