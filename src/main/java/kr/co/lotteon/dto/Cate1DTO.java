package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsCate1;
import kr.co.lotteon.entity.CsCate1;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cate1DTO {

    private int cate1;
    private String c1name;


    public CsCate1 toEntity(){
        return CsCate1.builder()
                .cate1(cate1)
                .c1name(c1name)
                .build();
    }
}
