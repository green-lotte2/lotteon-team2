package kr.co.lotteon.controller;

import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.transform.LogASTTransformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        List<CategoryDTO> cate = productService.getCategoryList();
        List<ProductDTO> newProduct = productService.findNewProduct();
        log.info(newProduct.toString());
        model.addAttribute("cate", cate);
        model.addAttribute("newProduct", newProduct);
        model.addAttribute("cate", productService.getCategoryList());
        return "/index";
    }

    @GetMapping("/regtest")
    public String regTest(Model model){

        List<CategoryDTO> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);

        return "/registertest";
    }

}
