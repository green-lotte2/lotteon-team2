package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;


@Mapper
public interface OrdersMapper {

    public List<OrdersDTO> selectOrders(String uid);

    public List<OrdersDTO> selectOrdersGroup(String uid);

    public List<OrdersDTO> selectAllOrders();

    List<OrdersDTO> selectOrderDetails(int ono);

    List<OrdersDTO> selectOrdersGroupByDate(String uid, LocalDate searchDate, LocalDate nowDate);

    public List<OrdersDTO> selectPoint(String uid);

    public  List<ReviewDTO> selectReview(int pno);

    Double reviewAverage(int pno);

    List<ReviewDTO> selectReviewsByUid(String uid);
}
