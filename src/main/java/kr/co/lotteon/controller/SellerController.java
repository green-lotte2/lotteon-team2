package kr.co.lotteon.controller;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CsService;
import kr.co.lotteon.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        log.info(authentication.getPrincipal().toString());
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        List<NoticeDTO> noticeDTOS = csService.selectNoticeListAll(1);
        model.addAttribute("noticeDTOS", noticeDTOS);


        log.info(authentication.getName());
        String uid = authentication.getName();
        List<OrdersDTO> monthSales = sellerService.selectOrderByMonthAndSeller("seller1");
        List<OrdersDTO> weekSales = adminService.selectOrderByWeek();
        List<OrdersDTO> cateName =  adminService.selectCountAndCateName();
        log.info(monthSales.toString());
        model.addAttribute("monthSales", monthSales);
        model.addAttribute("weekSales", weekSales);
        model.addAttribute("cateName", cateName);

        return "/seller/index";
    }

}
