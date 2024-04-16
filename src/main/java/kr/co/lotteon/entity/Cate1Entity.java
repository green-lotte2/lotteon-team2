package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.lotteon.dto.Cate1DTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cscate1")
public class Cate1Entity {

    @Id
    private int cate1;
    private String c1name;

    public Cate1DTO toDTO(){
        return Cate1DTO.builder()
                .cate1(cate1)
                .c1name(c1name)
                .build();
    }

}
