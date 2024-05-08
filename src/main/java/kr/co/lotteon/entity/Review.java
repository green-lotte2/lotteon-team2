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
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", nullable = false, updatable = false)
    private Product product;

    private String uid;
    private String content;
    private int rating;

    @Column(nullable = false)
    private LocalDateTime redate;

    @PrePersist
    protected void onCreate() {
        if (redate == null) {
            redate = LocalDateTime.now();
        }
    }
}
