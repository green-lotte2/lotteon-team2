package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SellerMapper {

    public List<OrdersDTO> selectOrderByMonthAndSeller(String sid);
}
