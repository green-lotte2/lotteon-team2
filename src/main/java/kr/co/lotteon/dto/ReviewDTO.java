package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private int rno;
    private int pno;
    private String uid;
    private String content;
    private int rating;
    private LocalDate redate;

}
