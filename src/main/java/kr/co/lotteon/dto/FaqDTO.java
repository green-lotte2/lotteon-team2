package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsFaq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {

    private int faqno;
    private int cate1;
    private int cate2;
    private String title;
    private String content;
    private String uid;
    private String regip;

    private LocalDateTime rdate;

    private int hit;

    // 추가필드
    private String c1name;
    private String c2name;
    private String rdatesub;

    public CsFaq toEntity(){
        return CsFaq.builder()
                .faqno(faqno)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .uid(uid)
                .regip(regip)
                .rdate(rdate)
                .hit(hit)
                .build();
    }
}
