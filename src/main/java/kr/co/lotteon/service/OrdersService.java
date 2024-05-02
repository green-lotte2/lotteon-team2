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
import org.apache.ibatis.javassist.compiler.ast.Keyword;
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
import java.time.LocalDate;
import java.time.Month;
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

    private final ModelMapper modelMapper;
    private final OrdersMapper ordersMapper;

    private final ProductimgRepository productimgRepository;


    /////////////////////////주문/////////////////////////////////
    public Orders insertOrder(OrdersDTO ordersDTO) {
        Orders orders = modelMapper.map(ordersDTO, Orders.class);

        return orderRepository.save(orders);
    }

    public void insertOrderDetail(OrdersDTO ordersDTO) {
        OrderDetail orderDetail = modelMapper.map(ordersDTO, OrderDetail.class);
        orderDetailRepository.save(orderDetail);
    }



    public List<OrdersDTO> selectOrders(String uid, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("ono");

        Page<Tuple> pageOrder = orderRepository.selectOrders(pageRequestDTO, pageable, uid);

        return pageOrder.getContent().stream()
                .map(tuple -> {
                    Orders orders = tuple.get(0, Orders.class);
                    OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);

                    ordersDTO.setPcount(tuple.get(6, Integer.class));
                    ordersDTO.setPno(tuple.get(1, Integer.class));
                    ordersDTO.setPname(tuple.get(3, String.class));
                    ordersDTO.setCompany(tuple.get(4, String.class));
                    ordersDTO.setMainimg(tuple.get(5, String.class));
                    ordersDTO.setPrice(tuple.get(8, Integer.class));
                    ordersDTO.setCate(tuple.get(2, Integer.class));


                    return ordersDTO;
                })
                .toList();
    }


    public List<OrdersDTO> selectAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    public List<OrdersDTO> selectOrdersGroup(String uid) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        if ("date".equals(pageRequestDTO.getType()) && pageRequestDTO.getKeyword() != null) {
            LocalDate nowDate = LocalDate.now();
            LocalDate searchDate = null;

            switch (pageRequestDTO.getKeyword()) {
                case "oneWeek":
                    searchDate = nowDate.minusDays(7);
                    break;
                case "15day":
                    searchDate = nowDate.minusDays(15);
                    break;
                case "Month":
                    searchDate = nowDate.minusMonths(1);
                    break;
            }

            return ordersMapper.selectOrdersGroupByDate(uid, searchDate, nowDate);
        } else {
            return ordersMapper.selectOrdersGroup(uid);
        }
    }




    @Transactional(readOnly = true)
    public List<OrdersDTO> getOrderDetails(int ono) {
        List<OrdersDTO> ordersDTO = ordersMapper.selectOrderDetails(ono);
        return ordersDTO;
    }



    public PageResponseDTO findOrderListByUid(String uid,PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("ono");
        Page<Orders> results = orderRepository.findAllByUid(uid, pageable);

        List<OrdersDTO> orderList = results.getContent().stream()
                .map(order -> modelMapper.map(order, OrdersDTO.class))
                .toList();

        int total = (int) results.getTotalElements();

        PageResponseDTO pageResponseDTO =  PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .orderList(orderList)
                .build();

        return pageResponseDTO;
    }
}

