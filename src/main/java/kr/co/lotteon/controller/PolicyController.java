package kr.co.lotteon.controller;

import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequiredArgsConstructor
@Controller
@RequestMapping("/policy")
public class PolicyController {

    private final ProductService productService;

    @GetMapping(value = "/buyer")
    public String buyer(Model model) {
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/policy/buyer";
    }

    @GetMapping(value = "/seller")
    public String seller(Model model) {
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/policy/seller";
    }

    @GetMapping(value = "/finance")
    public String finance(Model model) {
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/policy/finance";
    }

    @GetMapping(value = "/location")
    public String location(Model model) {
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/policy/location";
    }

    @GetMapping(value = "/privacy")
    public String privacy(Model model) {
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/policy/privacy";
    }
}
