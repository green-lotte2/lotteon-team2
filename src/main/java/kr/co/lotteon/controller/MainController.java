package kr.co.lotteon.controller;

<<<<<<< HEAD


=======
import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.service.ProductService;
>>>>>>> 840ccb66032d8827537ab78b5a50f6e153f0b885
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = {"/","/index"})
    public String index(Model model){

        return "/index";
    }

    @GetMapping("/regtest")
    public String regTest(Model model){

        List<CategoryResult> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);

        return "/registertest";
    }

}
