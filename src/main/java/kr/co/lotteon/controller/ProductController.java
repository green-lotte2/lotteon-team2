package kr.co.lotteon.controller;

import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/cart")
    public String cart(){
        return "/product/cart";
    }

    @GetMapping("/product/complete")
    public String complete(){
        return "/product/complete";
    }


    @GetMapping("/product/list")
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "pname", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> product = productService.findAllProducts(pageable);
        log.info("product112 : " + product);
        model.addAttribute("product", product);
        model.addAttribute("page", product);
        return "/product/list";
    }

    @GetMapping("/product/order")
    public String order(){
        return "/product/order";
    }

    @PostMapping("/product/order")
    public String productorder(){
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
