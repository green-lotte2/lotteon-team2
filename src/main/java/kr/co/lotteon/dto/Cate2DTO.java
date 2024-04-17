package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsCate2;
import kr.co.lotteon.entity.CsCateKey;
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


    public CsCate2 toEntity(){
        return CsCate2.builder()
                .csCateKey(new CsCateKey(cate1, cate2))
                .c2name(c2name)
                .build();
    }
}
