package kr.co.lotteon.service;

import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerService {
    private final SellerMapper sellerMapper;

    public List<OrdersDTO> selectOrderByMonthAndSeller(String sid){
        return sellerMapper.selectOrderByMonthAndSeller(sid);
    }

}
