package kr.co.lotteon.service;

import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.TypePageResponseDTO;
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

    public List<OrdersDTO> selectCountAndProductNameBySeller(String sid){
        return sellerMapper.selectCountAndProductNameBySeller(sid);
    }

    public List<OrdersDTO> selectOrderByWeekAndSeller(String sid){
        return sellerMapper.selectOrderByWeekAndSeller(sid);
    }

    public TypePageResponseDTO selectProductsBySearchAndSeller(PageRequestDTO pageRequestDTO){
        List<ProductDTO> productDTOS = sellerMapper.selectProductsBySearchAndSeller(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO,productDTOS.get(0).getLine(),productDTOS);
    }

}
