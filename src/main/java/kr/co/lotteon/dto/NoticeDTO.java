package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsNotice;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeDTO {

    private int noticeno;
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

    public CsNotice toEntity(){
        return CsNotice.builder()
                .noticeno(noticeno)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .uid(uid)
                .regip(regip)
                .rdate(rdate)
                .build();
    }
}
