package kr.co.lotteon.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminController {

    @GetMapping(value = {"/admin", "/admin/index"})
    public String index(){
        return "/admin/index";
    }

    @GetMapping("/admin/config/banner")
    public String banner(){
        return "/admin/config/banner";
    }

    @GetMapping("/admin/config/info")
    public String info(){
        return "/admin/config/info";
    }


    @GetMapping("/admin/product/list")
    public String list(){
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register(){
        return "/admin/product/register";
    }

    @GetMapping("/admin/charts")
    public String charts(){
        return "/admin/charts";
    }

    @GetMapping("/admin/tables")
    public String tables(){
        return "/admin/tables";
    }


    @GetMapping("/admin/error/404")
    public String error(){
        return "/admin/error/404";
    }



}











