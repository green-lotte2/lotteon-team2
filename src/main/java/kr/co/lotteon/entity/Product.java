package kr.co.lotteon.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    private String uid;
    private int cate;
    private String pname;
    @CreationTimestamp
    private LocalDateTime rdate;
    private int price;
    private int stock;
    private int deliprice;
    private String company;
    private int discount;
    private int point;
    private String info;
    private int delprice;
    private String size;
    private String color;
    private int hit;
    private String opname;
    private String opvalue;


}
