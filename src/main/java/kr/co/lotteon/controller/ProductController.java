package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.ProductRepository;
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
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final OrdersService ordersService;
    private final ProductRepository productRepository;
    private final ReviewService reviewService;
    private final AdminService adminService;

    @GetMapping("/product/cart")
    public String cart(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();  // 인증된 사용자 ID 추출


        List<ProductDTO> cartProducts = productService.getCartProductsByUid(uid);  // 사용자 ID를 기반으로 장바구니 상품 조회

        model.addAttribute("cartProducts", cartProducts);  // 모델에 장바구니 상품 목록 추가
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가

        return "/product/cart"; // 장바구니 뷰 페이지 반환
    }


    @ResponseBody
    @PostMapping("/product/cart/insert")
    public ResponseEntity<CartDTO> insertCartItem(Principal principal, @RequestBody CartDTO cartDTO) {
        String uid = principal.getName();
        log.info("uid : " + uid);
        cartDTO.setUid(uid);
        log.info("insertCart : " + cartDTO);

        cartService.insertCart(cartDTO);

        return ResponseEntity.ok(cartDTO);
    }

    @ResponseBody
    @PostMapping("/product/cart/update")
    public ResponseEntity<CartDTO> cartUpdate(Principal principal, @RequestBody CartDTO cartDTO) {
        String uid = principal.getName();
        cartDTO.setUid(uid);
        cartService.updateCate(cartDTO);
        return ResponseEntity.ok(cartDTO);
    }


    @GetMapping("/product/complete/{ono}")
    public String complete(@PathVariable("ono") int ono, Model model) {
        List<OrdersDTO> ordersDTO = ordersService.getOrderDetails(ono);

        ordersDTO.forEach(detail -> {
            // 상품 엔터티에서 추가 정보를 가져와 추가
            Product product = productRepository.findById(detail.getPno())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
            detail.setPrice(product.getPrice()); // 제품 가격 정보 추가
            detail.setDiscount(product.getDiscount());
            detail.setDeliveryPrice(product.getDeliprice());
            // 필요한 다른 정보도 추가 가능
        });


            // 총 상품 금액, 할인 금액, 배송비 계산
        int totalAmount = ordersDTO.stream()
                .mapToInt(detail -> detail.getPrice() * detail.getPcount())
                .sum();

        int totalDiscount = ordersDTO.stream()
                .mapToInt(detail -> (detail.getPrice() * detail.getDiscount() / 100) * detail.getPcount())
                .sum();
        log.info("totalDiscount1231 : " + totalDiscount);


        int deliveryFee = ordersDTO.stream()
                .mapToInt(detail -> detail.getDeliveryPrice())
                .sum();

        int finalPrice = totalAmount - deliveryFee - totalDiscount;

        // 모델에 값 추가
        model.addAttribute("cate", productService.getCategoryList());
        model.addAttribute("ordersDTO", ordersDTO);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);

        return "/product/complete";
    }

    @GetMapping("/product/view")
    public String viewProduct(@RequestParam("pno") int pno, @RequestParam(required = false) Integer cate, Model model) {
        ProductDTO productDTO = productService.findProductDTOById(pno);
        if (productDTO != null) {

            productService.productHitUpdate(productDTO);
            List<ReviewDTO> reviews = ordersService.selectReview(pno);

            Double Average = ordersService.reviewAverage(pno);
            if (Average == null) {
                Average = 0.0; // 평균 별점이 없는 경우 기본값 설정
            }
            List<BannerDTO> banners = adminService.selectBanner();
            model.addAttribute("banners", banners);

            model.addAttribute("Average", Average);
            model.addAttribute("cate", productService.getCategoryList());
            model.addAttribute("product", productDTO);
            model.addAttribute("reviews", reviews);

            log.info("Average1111 : " + Average);
            return "/product/view";
        } else {
            return "redirect:/product/list";
        }
    }

    @GetMapping("/product/list")
    public String list(Model model, @ModelAttribute ProductPageRequestDTO productPageRequestDTO, int cate) {

        // 서비스 메소드 호출
        ProductPageResponseDTO responseDTO = productService.getList(productPageRequestDTO, cate);
        responseDTO.setCate(cate); // cate 값을 responseDTO에 설정

        model.addAttribute("products", responseDTO.getDtoList());
        model.addAttribute("result1", responseDTO);
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가

        log.info("response : " + responseDTO);

        return "/product/list";
    }

    // 상품 검색 컨트롤러
    @GetMapping("/product/search")
    public String searchProducts(@ModelAttribute ProductPageRequestDTO productPageRequestDTO, Model model) {
        log.info(productPageRequestDTO.toString());

        ProductPageResponseDTO responseDTO = productService.getList(productPageRequestDTO, productPageRequestDTO.getCate());

        // 검색 키워드와 가격 정보를 뷰에 유지
        model.addAttribute("request", productPageRequestDTO);
        model.addAttribute("products", responseDTO.getDtoList());
        model.addAttribute("result", responseDTO);
        model.addAttribute("cate", productService.getCategoryList());

        return "/product/search"; // 검색 결과를 보여줄 뷰 이름
    }

    @PostMapping("/product/updateCartQuantity")
    @ResponseBody
    public ResponseEntity<?> updateCartQuantity(@RequestParam("pno") int pno, @RequestParam("quantity") int quantity) {
        try {
            Product product = productService.findProductById(pno).orElseThrow(() -> new Exception("Product not found"));
            if (quantity > 0 && quantity <= product.getStock()) {
                product.setStock(quantity); // 새로운 수량으로 업데이트
                productService.saveProduct(product);
                return ResponseEntity.ok().body("Updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid quantity");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating quantity: " + e.getMessage());
        }
    }

    // 장바구니에서 상품 삭제
    @PostMapping("/cart/delete")
    public ResponseEntity<?> deleteCart(Principal principal, @RequestBody Map<String, int[]> requestData) {
        int[] pnos = requestData.get("pnos");
        log.info("pnos : " + pnos);
        return cartService.deleteCartItems(principal.getName(), pnos);
    }



}

