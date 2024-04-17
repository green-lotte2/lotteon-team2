package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.dto.Cate2DTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cate2")

public class CsCate2 {


    @EmbeddedId
    CsCateKey csCateKey;

    private String c2name;

    public Cate2DTO toDTO(){
        return Cate2DTO.builder()
                .cate1(csCateKey.getCate1())
                .cate2(csCateKey.getCate2())
                .c2name(c2name)
                .build();
    }
}
