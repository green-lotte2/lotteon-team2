package kr.co.lotteon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String uid;
    private String pass;
    private String name;
    private String gender;
    private String email;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String role = "USER";
    private String regip;
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;

    private int point;
    private String provider;
    private int grade;
    private String location;

    private String company;
    private String represent;
    private String regnum;
    private String reportnum;
    private String cohp;
    private String fax;

}
