package kr.co.lotteon.dto;

// 필요한 임포트

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int dono;

;
    private String options;
    private int price;

    private int ono;
    private int pno;
    private String pname;
    private int pcount;
    private LocalDate odate;
    private String uid;
    private String state;



    public OrderDetailDTO(int ono, int pno, String pname, int pcount, LocalDate odate, String uid, String state) {
        // 생성자 내용
        this.ono = ono;
        this.pno = pno;
        this.pname = pname;
        this.pcount = pcount;
        this.odate = LocalDate.parse(String.valueOf(odate));
        this.uid = uid;
        this.state = state;
    }



}
