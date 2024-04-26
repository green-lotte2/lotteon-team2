package kr.co.lotteon.entity;


import jakarta.persistence.*;
import kr.co.lotteon.dto.ReplyDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class CsReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyno;

    private int qnano;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime rdate;
    private String writer;

    public ReplyDTO toDTO() {
        return  ReplyDTO.builder()
                .replyno(replyno)
                .qnano(qnano)
                .content(content)
                .rdate(rdate)
                .writer(writer)
                .build();
    }
}
