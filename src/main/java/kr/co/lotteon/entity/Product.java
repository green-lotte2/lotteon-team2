package kr.co.lotteon.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    private int pno;
    private String uid;
    private int cate;
    private String pname;
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

}
