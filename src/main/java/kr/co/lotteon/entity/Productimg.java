package kr.co.lotteon.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "productimg")
public class Productimg {

    @Id
    private int pno;
    private String mainimg;
    private String subimg;
    private String detailimg;

    @OneToOne
    @JoinColumn(name = "pno", referencedColumnName = "pno")
    private Product product;
}
