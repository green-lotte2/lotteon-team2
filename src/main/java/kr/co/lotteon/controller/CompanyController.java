package kr.co.lotteon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {

    @GetMapping(value = {"/company", "/company/index"})
    public String index(){
        return "/company/index";
    }

    @GetMapping("/company/culture")
    public String culture(){
        return "/company/culture";
    }

    @GetMapping("/company/blog")
    public String blog(){
        return "/company/blog";
    }

    @GetMapping("/company/jobPosting")
    public String jobPosting(){
        return "/company/jobPosting";
    }

    @GetMapping("/company/media")
    public String media(){
        return "/company/media";
    }
}
