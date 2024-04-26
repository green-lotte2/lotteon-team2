package kr.co.lotteon.dto;

// 필요한 임포트

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int dono;
    private int pno;
    private int pcount;
    private String options;
    private int price;
}
