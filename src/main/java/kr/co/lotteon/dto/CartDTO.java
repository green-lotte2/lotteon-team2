package kr.co.lotteon.dto;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private String uid;
    private int pno;
    private int pcount;
}
