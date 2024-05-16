package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CsService;
import kr.co.lotteon.service.ProductService;
import kr.co.lotteon.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
public class SellerController {

    private final CsService csService;
    private final ProductService productService;
    private final SellerService sellerService;
    private final AdminService adminService;

    @GetMapping(value = {"/seller","/seller/index"})
    public String index(Model model, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        List<NoticeDTO> noticeDTOS = csService.selectNoticeListAll(1);
        model.addAttribute("noticeDTOS", noticeDTOS);


        log.info(authentication.getName());
        String uid = authentication.getName();
        List<OrdersDTO> monthSales = sellerService.selectOrderByMonthAndSeller(uid);
        List<OrdersDTO> weekSales = sellerService.selectOrderByWeekAndSeller(uid);
        List<OrdersDTO> cateName =  sellerService.selectCountAndProductNameBySeller(uid);
        log.info(monthSales.toString());
        log.info(weekSales.toString());
        log.info(cateName.toString());

        model.addAttribute("user", adminService.selectRegUser());
        model.addAttribute("monthSales", monthSales);
        model.addAttribute("weekSales", weekSales);
        model.addAttribute("cateName", cateName);

        return "/seller/index";
    }

    @GetMapping("/seller/product/register")
    public String productRegister(Model model, PageRequestDTO pageRequestDTO, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        List<CategoryDTO> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);

        return "/seller/product/register";
    }

    @PostMapping("/seller/product/register")
    public String productRegister(ProductDTO productDTO,
                                  Authentication authentication,
                                  @RequestParam("mainImg") MultipartFile fileA,
                                  @RequestParam("detailImg") MultipartFile fileD){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        productDTO.setSid(authentication.getName());

        if(productDTO.getPno()!=0){
            Product product = productService.findProductById(productDTO.getPno()).get();
            productDTO.setRdate(product.getRdate());
        }

        Product product = productService.insertProduct(productDTO);

        if (!fileA.isEmpty()){
            List<MultipartFile> files = new ArrayList<>();
            files.add(fileA);
            files.add(fileD);

            ProductimgDTO imgDTO = new ProductimgDTO();
            imgDTO.setPno(productDTO.getPno());
            imgDTO.setFiles(files);
            imgDTO.setPno(product.getPno());

            productService.imgUpload(imgDTO, productDTO.getCate());
            productService.insertImg(imgDTO);
        }

        return "redirect:/seller/product/list";
    }

    @GetMapping("/seller/product/list")
    public String productList(Model model, PageRequestDTO pageRequestDTO, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        String uid = authentication.getName();
        pageRequestDTO.setSeller(uid);

        TypePageResponseDTO productDtos = sellerService.selectProductsBySearchAndSeller(pageRequestDTO);

        model.addAttribute("adminProducts", productDtos);
        return "/seller/product/list";
    }

    @GetMapping("/seller/product/modify")
    public String ProductModify(Model model, int pno){

        Product product = productService.findProduct(pno);
        List<CategoryDTO> cate = productService.getCategoryList();

        model.addAttribute("product", product);
        model.addAttribute("cate", cate);

        return "/seller/product/modify";
    }

    @GetMapping("/seller/order/list")
    public String OrderList(Model model, PageRequestDTO pageRequestDTO, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        pageRequestDTO.setSeller(authentication.getName());
        TypePageResponseDTO ordersDTOS = sellerService.selectOrderBySellerGroup(pageRequestDTO);
        TypePageResponseDTO orderProduct = sellerService.selectOrdersBySeller(pageRequestDTO);
        model.addAttribute("orders", ordersDTOS);
        model.addAttribute("products", orderProduct);
        return "/seller/order/list";
    }

    @GetMapping("/seller/order/sales")
    public String OrderSales(Model model, PageRequestDTO pageRequestDTO, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        String sid = authentication.getName();
        pageRequestDTO.setSeller(sid);
        TypePageResponseDTO products = sellerService.selectOrderByProduct(pageRequestDTO);
        List<OrdersDTO> orders = sellerService.selectOrderByMonthAndSellerAndProduct(sid);
        log.info("이거다 이거"+orders);
        model.addAttribute("ordersList",products);
        model.addAttribute("orders",orders);

        return "/seller/order/sales";
    }

    @GetMapping("/seller/product/delete")
    public String adminDeleteProduct(int pno) {
        Product product = productService.findProduct(pno);
        adminService.adminDeleteProduct(product);
        return "redirect:/admin/product/list";
    }

    @PostMapping("/seller/product/delete")
    public String adminDeleteProduct(@RequestParam List<String> checkbox) {
        for (String pno : checkbox) {
            int productId = Integer.parseInt(pno);
            Product product = productService.findProduct(productId);
            adminService.adminDeleteProduct(product);
            log.info("deletePno : " + productId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/product/list";
    }

}
