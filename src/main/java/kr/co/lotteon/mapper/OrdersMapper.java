package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.OrdersDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OrdersMapper {

    public List<OrdersDTO> selectOrders(String uid);
    public List<OrdersDTO> selectOrdersGroup(String uid);

    List<OrdersDTO> selectOrderDetails(int ono);
}
