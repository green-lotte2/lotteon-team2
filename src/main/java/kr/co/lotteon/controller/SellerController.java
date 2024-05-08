package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CsService;
import kr.co.lotteon.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {

    private final CsService csService;
    private final AdminService adminService;
    private final SellerService sellerService;

    @GetMapping(value = {"/seller","/seller/index"})
    public String index(Model model, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        List<NoticeDTO> noticeDTOS = csService.selectNoticeListAll(1);
        model.addAttribute("noticeDTOS", noticeDTOS);


        log.info(authentication.getName());
        String uid = authentication.getName();
        List<OrdersDTO> monthSales = sellerService.selectOrderByMonthAndSeller("seller1");
        List<OrdersDTO> weekSales = sellerService.selectOrderByWeekAndSeller("seller1");
        List<OrdersDTO> cateName =  sellerService.selectCountAndProductNameBySeller("seller1");
        log.info(monthSales.toString());
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

        TypePageResponseDTO productDtos = sellerService.selectProductsBySearchAndSeller(pageRequestDTO);
        return "/seller/register";
    }

    @GetMapping("/seller/product/list")
    public String productList(Model model, PageRequestDTO pageRequestDTO, Authentication authentication){

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        String uid = authentication.getName();
        pageRequestDTO.setSeller("seller1");

        TypePageResponseDTO productDtos = sellerService.selectProductsBySearchAndSeller(pageRequestDTO);

        model.addAttribute("adminProducts", productDtos);
        return "/seller/product/list";
    }

}
