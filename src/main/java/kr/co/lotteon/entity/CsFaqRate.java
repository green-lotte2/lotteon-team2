package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faqrate")
public class CsFaqRate {

    @Id
    private int faqno;
    @Id
    private String uid;
    private int rate;
}
