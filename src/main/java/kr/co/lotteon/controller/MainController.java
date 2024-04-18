package kr.co.lotteon.controller;

import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model){

        List<CategoryResult> cate = productService.getCategoryList();
        List<Product> newProduct = productService.findNewProduct();
        model.addAttribute("cate", cate);
        model.addAttribute("newProduct", newProduct);
        return "/index";
    }

    @GetMapping("/regtest")
    public String regTest(Model model){

        List<CategoryResult> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);

        return "/registertest";
    }

}
