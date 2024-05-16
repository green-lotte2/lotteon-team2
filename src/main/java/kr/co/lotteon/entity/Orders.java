package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ono;
    private String uid;
    @CreationTimestamp
    private LocalDate odate;
    private int usepoint;
    private int savepoint;
    private String receiver;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payment;
    private int total;




}