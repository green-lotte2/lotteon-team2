package kr.co.lotteon.service;

import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.Review;
import kr.co.lotteon.mapper.MypageMapper;
import kr.co.lotteon.mapper.ReviewMapper;
import kr.co.lotteon.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final MypageMapper mypageMapper;

    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, MypageMapper mypageMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.mypageMapper = mypageMapper;
    }


    public void saveReview(ReviewDTO reviewDTO) {
        mypageMapper.insertReview(reviewDTO);
    }

    public List<ReviewDTO> findReviewsByUserId(String uid) {
        List<Review> reviews = reviewRepository.findByUid(uid);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> findReviewsByProductId(int pno) {
        List<Review> reviews = reviewRepository.findByProduct_Pno(pno);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }



}