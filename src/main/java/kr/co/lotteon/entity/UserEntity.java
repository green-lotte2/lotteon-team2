package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "UserEntity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int seq;

    private String buyerterms;
    private String sellerterms;
    private String transaction;
    private String privacy;
    private String location;
}

