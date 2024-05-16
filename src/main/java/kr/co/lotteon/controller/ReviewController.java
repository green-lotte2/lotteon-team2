package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.ProductService;
import kr.co.lotteon.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final AdminService adminService;

    /**
     * 특정 상품에 대한 리뷰 작성 폼을 표시합니다.
     */
    @GetMapping("/mypage/writeReview")
    public String writeReviewForm(Authentication authentication, @RequestParam("pno") int pno, Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        }
        ProductDTO product = productService.findProductDTOById(pno);
        if (product == null) {
            return "redirect:/product/list";
        }

        model.addAttribute("product", product);
        model.addAttribute("pageId", "writeReview");
        return "/mypage/writeReview";
    }

    /**
     * 리뷰를 저장하고 상품 상세 페이지로 리다이렉트합니다.
     */
    @PostMapping("/mypage/review")
    public String saveReview(@ModelAttribute ReviewDTO reviewDTO) {
        log.info("ReviewDTO: " + reviewDTO); // 디버깅 로그 추가
        reviewService.saveReview(reviewDTO);

        // 리뷰 저장 후 /mypage/order로 리다이렉트
        return "redirect:/mypage/order";
    }


    /**
     * 로그인된 사용자가 작성한 모든 리뷰를 표시하는 페이지를 보여줍니다.
     */
    @GetMapping("/mypage/review")
    public String myReviews(Authentication authentication, Model model,
                            @RequestParam(defaultValue = "1") int pg,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) Integer cate) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        }

        ProductPageRequestDTO productPageRequestDTO = ProductPageRequestDTO.builder()
                .pg(pg)
                .size(size)
                .build();

        int cateInt = cate != null ? cate : 0;
        ProductPageResponseDTO responseDTO = productService.getList(productPageRequestDTO, cateInt);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();

        List<BannerDTO> banners = adminService.selectBanner();
        model.addAttribute("banners", banners);

        List<ReviewDTO> reviews = reviewService.selectReviewsByUid(uid);
        model.addAttribute("result", responseDTO);
        model.addAttribute("reviews", reviews);
        return "/mypage/review"; // 사용자 리뷰 보기 페이지 반환
    }
}