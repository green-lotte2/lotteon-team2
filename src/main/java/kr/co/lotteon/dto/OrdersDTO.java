package kr.co.lotteon.dto;

import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.Orders;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private int ono;
    private String uid;
    private int pno;
    private LocalDate odate;
    private int usepoint;
    private int savepoint;
    private String receiver;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payment;
    private String request;
    private int total;
    private int pcount;
    private String options;
    private int discount;
    private int dfee;

    // add
    private int cate;
    private String mainimg;
    private String company;
    private String pname;
    private int price;
    private int count;

    // 주문 상세 정보
    private List<OrderDetailDTO> orderDetails;
    // 계산된 금액들
    private int totalProductPrice;
    private int totalDiscount;
    private int totalShipping;
    private int finalPrice;
}
