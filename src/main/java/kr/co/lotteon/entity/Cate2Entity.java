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
public class Cate2Entity {

    @EmbeddedId
    CateKey cateKey;

    private String c2name;

    public Cate2DTO toDTO(){
        return Cate2DTO.builder()
                .cate1(cateKey.getCate1())
                .cate2(cateKey.getCate2())
                .c2name(c2name)
                .build();
    }
}
