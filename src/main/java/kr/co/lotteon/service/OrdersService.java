package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.mapper.AdminMapper;
import kr.co.lotteon.mapper.OrdersMapper;
import kr.co.lotteon.mapper.ProductMapper;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Value("${img.upload.path}")
    private String imgUploadPath;

    private final ModelMapper modelMapper;
    private final OrdersMapper ordersMapper;

    private final ProductimgRepository productimgRepository;

    /////////////////////////주문/////////////////////////////////
    public Orders insertOrder(OrdersDTO ordersDTO){
        Orders orders = modelMapper.map(ordersDTO, Orders.class);

        return orderRepository.save(orders);
    }

    public void insertOrderDetail(OrdersDTO ordersDTO){
        OrderDetail orderDetail = modelMapper.map(ordersDTO, OrderDetail.class);
        orderDetailRepository.save(orderDetail);
    }

    public List<OrdersDTO> selectOrders(String uid){
        return ordersMapper.selectOrders(uid);
    }

    @Transactional(readOnly = true)
    public OrdersDTO getOrderDetails(int ono) {
        return orderRepository.findById(ono)
                .map(order -> modelMapper.map(order, OrdersDTO.class))
                .orElse(null); // 주문을 찾을 수 없는 경우 null 반환
    }

}


