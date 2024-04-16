package kr.co.lotteon.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SellerDTO {

    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private String location;
    private String regip;
    private String leaveDate;
    private String userpoint;
    private String zip;
    private String addr1;
    private String addr2;
    private String role = "USER";
    private String provider;
    private String rdate;
    private String grade;
}
