package kr.co.lotteon.entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private int cno;
    private String uid;
    private int pno;
    private int pcount;
    private String options;

}
