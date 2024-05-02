package kr.co.lotteon.dto;


import jakarta.persistence.JoinColumn;
import kr.co.lotteon.entity.CsReply;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private int replyno;

    private int qnano;
    private String rcontent;
    private LocalDateTime rdate;
    private String writer;


    public CsReply toEntity() {
        return CsReply.builder()
                .replyno(replyno)
                .rcontent(rcontent)
                .qnano(qnano)
                .rdate(rdate)
                .writer(writer)
                .build();
    }
}
