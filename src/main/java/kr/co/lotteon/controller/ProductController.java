package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Console;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    @GetMapping("/product/cart")
    public String cart(){
        return "/product/cart";
    }
    @GetMapping("/product/cart1")
    public String cart1(){
        return "/product/cart1";
    }

    @GetMapping("/product/complete")
    public String complete(){
        return "/product/complete";
    }

    @GetMapping("/product/list")
    public String list(){
        return "/product/list";
    }

    @GetMapping("/product/order")
    public String order(){
        return "/product/order";
    }

    @GetMapping("/product/search")
    public String search(){
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view(){
        return "/product/view";
    }
}
