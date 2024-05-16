package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsNotice;
import kr.co.lotteon.entity.Product;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.rmi.server.UID;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int pno;
    private String sid;
    private int cate;
    private String pname;
    private LocalDateTime rdate;
    private LocalDateTime deldate;
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
    private int pcount;
    private String options;

    private String mainimg;
    private String subimg;
    private String detailimg;

    private int line;

    public Product toEntity(){
        return Product.builder()
                .pno(pno)
                .sid(sid)
                .cate(cate)
                .pname(pname)

                .build();
    }


}
