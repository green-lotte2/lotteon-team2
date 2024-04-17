package kr.co.lotteon.controller;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/register")
    public String productRegister(ProductDTO productDTO,
                                  @RequestParam("imgMain") MultipartFile fileA,
                                  @RequestParam("imgSub1") MultipartFile fileB,
                                  @RequestParam("imgSub2") MultipartFile fileC,
                                  @RequestParam("imgDetail") MultipartFile fileD){

        log.info("productRegister");
        log.info(""+productDTO);

        List<MultipartFile> files = new ArrayList<>();
        files.add(fileA);
        files.add(fileB);
        files.add(fileD);

        ProductimgDTO imgDTO = new ProductimgDTO();

        imgDTO.setPno(productDTO.getPno());
        imgDTO.setFiles(files);

        productService.imgUpload(imgDTO, productDTO.getCate());
        Product product = productService.insertProduct(productDTO);
        imgDTO.setPno(product.getPno());
        productService.insertImg(imgDTO);

        return "/admin/product/list";
    }

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
    public String search(
                        Model model,
                        String search,
                        @PageableDefault(size = 10, sort = "pname", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Product> resultList = productService.findByPname(pageable, search);
        for(Product result : resultList){
            log.info(result.toString());
        }
        model.addAttribute("product", resultList);
        model.addAttribute("page", resultList);
        return "/product/list";
    }

    @GetMapping("/product/view")
    public String view(){
        return "/product/view";
    }



}
