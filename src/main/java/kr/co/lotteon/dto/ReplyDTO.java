package kr.co.lotteon.dto;


import kr.co.lotteon.entity.CsReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private int replyno;
    private int qnano;
    private String title;
    private String content;
    private LocalDateTime rdate;
    private String writer;

    public CsReply toEntity() {
        return CsReply.builder()
                .replyno(replyno)
                .qnano(qnano)
                .title(title)
                .content(content)
                .rdate(rdate)
                .writer(writer)
                .build();
    }
}
