package kr.co.lotteon.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminProductController {

    @GetMapping("/admin/product/register")
    public String adminProductRegister(){

        return "/admin/product/register";
    }

    @GetMapping("/admin/product/list")
    public String adminProductList(){

        return "/admin/product/list";
    }

    @GetMapping("/admin/product/modify")
    public String adminProductModify(){
        return "/admin/product/modify";
    }

    @GetMapping("/admin/order/list")
    public String adminOrderList(){
        return "/admin/order/list";
    }

}
