package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user")
public class User{

    @Id
    private String uid;
    private String pass;
    private String name;
    private String gender;
    private String email;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;

    @Builder.Default
    private String role = "USER";
    private String regip;

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
}


