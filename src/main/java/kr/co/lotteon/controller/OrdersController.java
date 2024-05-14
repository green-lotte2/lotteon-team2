package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.mapper.OrdersMapper;
import kr.co.lotteon.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Console;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrdersController {

    private final ProductService productService;
    private final OrdersService ordersService;
    private final CartService cartService;
    private final UserService userService;
    private final AdminService adminService;
    private final OrdersMapper ordersMapper;

    @GetMapping("/product/order")
    public String order(Authentication authentication, Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();  // 인증된 사용자 ID 추출

        List<ProductDTO> cartProducts = productService.getCartProductsByUid(uid);  // 사용자 ID를 기반으로 장바구니 상품 조회
        UserDTO user = userService.selectUserDetail(uid);
        log.info("dddddd" + user);
        model.addAttribute("user", user);
        model.addAttribute("cartProducts", cartProducts);  // 모델에 장바구니 상품 목록 추가
        model.addAttribute("cate", productService.getCategoryList());

        return "/product/order";
    }

    @PostMapping("/product/order")
    public String productOrder(OrdersDTO ordersDTO, @RequestParam List<String> checkbox) {

        Orders orders = ordersService.insertOrder(ordersDTO);
        String uid = ordersDTO.getUid();
        int ono = orders.getOno();
        for (String select : checkbox) {
            OrdersDTO ordersDTO1 = new OrdersDTO();
            ordersDTO1.setOno(ono);
            log.info("select!!! : " + select);
            String[] productInfo = select.split("%");

            ordersDTO1.setPno(Integer.parseInt(productInfo[0]));
            ordersDTO1.setPcount(Integer.parseInt(productInfo[1]));
            ordersDTO1.setPrice(Integer.parseInt(productInfo[2]));

            ordersDTO1.setOptions(null);
            if (productInfo.length > 3) {
                String[] optionsArray = Arrays.copyOfRange(productInfo, 3, productInfo.length);
                String options = String.join(" ", optionsArray);
                ordersDTO1.setOptions(options);
            }
            ordersService.insertOrderDetail(ordersDTO1);
            cartService.orderCartItems(uid, ordersDTO1.getPno());
        }
        userService.updateUserPoint(uid, ordersDTO.getUsepoint(), ordersDTO.getSavepoint());
        return "redirect:/product/complete/" + ono;
    }


    @GetMapping("/mypage/order")
    public String myOrder(Authentication authentication, Model model, PageRequestDTO pageRequestDTO) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login";
        }

        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();

        List<OrdersDTO> orders = ordersService.selectOrders(uid, pageRequestDTO);
        PageResponseDTO pageResponseDTO = ordersService.findOrderListByUid(uid, pageRequestDTO);
        List<BannerDTO> banners = adminService.selectBanner();
        model.addAttribute("banners", banners);
        model.addAttribute("orders", orders);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        log.info(orders.toString());
        return "/mypage/order";
    }
}


