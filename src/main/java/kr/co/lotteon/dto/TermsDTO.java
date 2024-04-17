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

    // 추가필드
    private String type;
    private boolean agree1;
    private boolean agree2;
    private boolean agree3;
    private boolean agree4;


    public String locationYesNo(){
        return agree4 ? "Y" : "N";
    }
}
