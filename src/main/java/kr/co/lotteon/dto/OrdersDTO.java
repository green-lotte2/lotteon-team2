package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime odate;
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

    // add
    private String cate;
    private String mainimg;
    private String company;
    private String pname;
    private int price;
    private int count;
}
