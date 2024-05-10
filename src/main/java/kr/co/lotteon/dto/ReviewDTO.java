package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private int rno;
    private int pno;
    private String pname;
    private String uid;
    private String content;
    private int rating;
    private String options;
    private LocalDateTime redate;


}
