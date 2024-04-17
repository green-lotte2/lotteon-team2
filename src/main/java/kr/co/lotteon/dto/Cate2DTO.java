package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Cate2Entity;
import kr.co.lotteon.entity.CateKey;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cate2DTO {

    private int cate1;
    private int cate2;
    private String c2name;


    public Cate2Entity toEntity(){
        return Cate2Entity.builder()
                .cateKey(new CateKey(cate1, cate2))
                .c2name(c2name)
                .build();
    }
}
