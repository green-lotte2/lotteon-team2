package kr.co.lotteon.service;

import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.Review;
import kr.co.lotteon.mapper.MypageMapper;
import kr.co.lotteon.mapper.OrdersMapper;
import kr.co.lotteon.mapper.ReviewMapper;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final MypageMapper mypageMapper;
    private final ProductRepository productRepository;
    private final OrdersMapper ordersMapper;



    public void saveReview(ReviewDTO reviewDTO) {
        mypageMapper.insertReview(reviewDTO);
    }

    public List<ReviewDTO> findReviewsByUserId(String uid) {
        // 리뷰 조회
        List<Review> reviews = reviewRepository.findByUid(uid);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review each : reviews) {
            reviewDTOs.add(modelMapper.map(each, ReviewDTO.class));
        }
        log.info("reviewDTOs : " + reviewDTOs);
        return reviewDTOs;
    }

    public List<ReviewDTO> findReviewsByProductId(int pno) {
        List<Review> reviews = reviewRepository.findByProduct_Pno(pno);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }


    public List<ReviewDTO> selectReviewsByUid(String uid){
        return ordersMapper.selectReviewsByUid(uid);
    }
}