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
@Table(name = "seller")
public class Seller {

    @Id
    private String uid;

    private String company;
    private String represent;
    private String regnum;
    private String reportnum;
    private String cohp;
    private String fax;
}
