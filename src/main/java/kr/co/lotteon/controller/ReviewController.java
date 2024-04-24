package kr.co.lotteon.controller;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.service.ProductService;
import kr.co.lotteon.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ProductService productService;
    private final ReviewService reviewService;


    // pno에 해당하는 상품을 조회하여 모델에 추가하고, 리뷰 작성 페이지로 이동합니다.
    @GetMapping("/mypage/writeReview")
    public String writeReviewForm(@RequestParam("pno") int pno, Model model) {
        ProductDTO product = productService.findProductDTOById(pno);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "/mypage/writeReview";
    }

    // 리뷰를 저장합니다.
    @PostMapping("/product/review")
    public String saveReview(@ModelAttribute ReviewDTO reviewDTO) {
        reviewService.saveReview(reviewDTO);
        return "redirect:/product/view?pno=" + reviewDTO.getPno(); // 저장 후 상품 상세페이지로 리다이렉트합니다.
    }

    // 리뷰 페이지로 이동합니다.
/*
    @GetMapping("/mypage/review")
    public String myReviews(Model model, String uid) {
        // 여기서 리뷰 목록을 가져오는 서비스 메서드 호출
        List<ReviewDTO> reviews = reviewService.findReviewsByUserId(uid);
        // 모델에 리뷰 목록 추가
        model.addAttribute("reviews", reviews);

        // 해당하는 뷰 이름 반환
        return "/mypage/writeReview";
    }
*/

}
