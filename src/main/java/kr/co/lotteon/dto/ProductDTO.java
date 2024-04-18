package kr.co.lotteon.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

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
    private String pname;//
    private LocalDateTime rdate;
    private int price;//
    private int stock;//
    private int deliprice;//
    private String company;//
    private int discount;//
    private int point;//
    private String info;//
    private int delprice;
    private String size;
    private String color;
    private int hit;

    private String mainimg;
    private String subimg;
    private String detailimg;

}
