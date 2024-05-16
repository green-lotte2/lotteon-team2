package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "userdetail")
public class UserDetail {

    @Id
    private String uid;

    @Builder.Default
    private int point = 0;
    private int grade;

    private char location;



}
