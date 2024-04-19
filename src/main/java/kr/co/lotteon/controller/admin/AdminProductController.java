package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final ProductService productService;
    private final AdminService adminService;


    @GetMapping("/admin/product/register")
    public String adminProductRegister(Model model){
        List<CategoryResult> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);
        log.info("adminProductRegister1.." + cate);

        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String adminProductRegister(ProductDTO productDTO,
                                       @RequestParam("mainImg") MultipartFile fileA,
                                       @RequestParam("subImg1") MultipartFile fileB,
                                       @RequestParam("subImg2") MultipartFile fileC,
                                       @RequestParam("detailImg") MultipartFile fileD){
        log.info("adminProductRegister2");
        log.info("" + productDTO);

        List<MultipartFile> files = new ArrayList<>();
        files.add(fileA);
        files.add(fileB);
        files.add(fileD);

        ProductimgDTO imgDTO = new ProductimgDTO();

        imgDTO.setPno(productDTO.getPno());
        imgDTO.setFiles(files);

        productService.imgUpload(imgDTO, productDTO.getCate());
        Product product = productService.insertProduct(productDTO);

        log.info("product :" + product);

        imgDTO.setPno(product.getPno());
        log.info("pno.. ");
        productService.insertImg(imgDTO);

        return "/admin/product/register";
    }


    @GetMapping("/admin/product/list")
    public String adminProductList(Model model){

        List<ProductDTO> adminProducts = adminService.selectProducts();
        log.info("adminProducts" + adminProducts);

        model.addAttribute("adminProducts", adminProducts);

        return "/admin/product/list";
    }

    @PostMapping("/admin/product/delete")
    public String adminDeleteProduct(@RequestParam List<String> checkbox){
        for(String pno : checkbox){
            int productId = Integer.parseInt(pno);
            adminService.adminDeleteProduct(productId);
            log.info("deletePno : " + productId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/product/list";
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
