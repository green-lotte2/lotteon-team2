package kr.co.lotteon.entity;


import jakarta.persistence.*;
import kr.co.lotteon.dto.ProductDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    private String sid;
    private int cate;
    private String pname;
    @CreationTimestamp
    private LocalDateTime rdate;
    private LocalDateTime deldate;

    private int price;
    private int stock;
    private int deliprice;
    private String company;
    private int discount;
    private int point;
    private String info;
    private int hit;
    private String opname;
    private String opvalue;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Productimg productimg;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}