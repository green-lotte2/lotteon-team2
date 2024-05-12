package kr.co.lotteon.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    private int state;
    private String stname;

    // 추가필드

    // 추가필드
    private int ono;
    private int pno;
    private int pcount;
    private String pname;
    private String odate;
    private String uid;

    // state 필드를 기반으로 배송 상태명을 반환하는 getter 메서드 추가
    public String getStname() {
        if (this.state == 1) {
            return "배송준비";
        } else if (this.state == 2) {
            return "배송중";
        } else if (this.state == 3) {
            return "배송완료";
        } else if (this.state == 4) {
            return "반품";
        } else if (this.state == 5) {
            return "환불";
        } else {
            return "알 수 없음";
        }
    }

}
