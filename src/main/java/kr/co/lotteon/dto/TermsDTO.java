package kr.co.lotteon.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsDTO {

    private int seq;
    private String buyerterms;
    private String sellerterms;
    private String transaction;
    private String privacy;
    private String location;


}
