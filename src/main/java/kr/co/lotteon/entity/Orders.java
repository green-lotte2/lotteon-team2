package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    private int ono;
    private String uid;
    @CreationTimestamp
    private LocalDateTime odate;
    private int usepoint;
    private String receiver;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payment;
    private String request;
    private int total;
}