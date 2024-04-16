package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Cate1Entity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cate1DTO {

    private int cate1;
    private String c1name;

    public Cate1Entity toEntity(){
        return Cate1Entity.builder()
                .cate1(cate1)
                .c1name(c1name)
                .build();
    }
}
