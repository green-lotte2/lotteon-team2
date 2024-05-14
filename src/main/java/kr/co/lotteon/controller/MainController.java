package kr.co.lotteon.controller;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.AdminService;
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
    private final AdminService adminService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        List<CategoryDTO> cate = productService.getCategoryList();
        List<ProductDTO> newProduct = productService.findNewestProduct();
        List<ProductDTO> bestProduct = productService.findBestProduct();
        List<ProductDTO> recommendProduct = productService.findRecommendProduct();
        List<ProductDTO> hitProduct = productService.findHighHitProduct();
        List<ProductDTO> discountProduct = productService.findDiscountProduct();

        List<BannerDTO> banners = adminService.selectBanner();

        model.addAttribute("cate", cate);
        model.addAttribute("newProduct", newProduct);
        model.addAttribute("bestProduct", bestProduct);
        model.addAttribute("recommendProduct", recommendProduct);
        model.addAttribute("hitProduct", hitProduct);
        model.addAttribute("discountProduct", discountProduct);

        model.addAttribute("banners", banners);

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
