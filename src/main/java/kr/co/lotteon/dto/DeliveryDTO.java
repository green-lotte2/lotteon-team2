package kr.co.lotteon.dto;


import kr.co.lotteon.entity.CsReply;
import kr.co.lotteon.entity.Delivery;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    private int state;
    private String stname;


    // 추가필드
    private int ono;
    private int pno;
    private int pcount;
    private String pname;
    private String odate;
    private String uid;

    // 배송 상태에 따라 배송 상태명을 설정하는 메서드 추가
    public void setStname() {
        if (this.state == 1) {
            this.stname = "배송준비";
        } else if (this.state == 2) {
            this.stname = "배송중";
        } else if (this.state == 3) {
            this.stname = "배송완료";
        } else if (this.state == 4) {
            this.stname = "반품";
        } else if (this.state == 5) {
            this.stname = "환불";
        }

    }


    // DeliveryDTO 생성자
    public DeliveryDTO(int ono, int pno, String pname, int pcount, LocalDate odate, String uid, int state, String stname) {
        // 생성자 내용
        this.ono = ono;
        this.pno = pno;
        this.pname = pname;
        this.pcount = pcount;
        this.odate = String.valueOf(odate);
        this.uid = uid;
        this.state = state;
        this.stname = stname;
    }


}
