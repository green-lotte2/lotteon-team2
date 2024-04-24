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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyno;


    @ManyToOne
    @JoinColumn(name = "qnano", nullable = false)
    private CsQna qnano;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime rdate;
    private String writer;

    public ReplyDTO toDTO(){
        return ReplyDTO.builder()
                .replyno(replyno)
                .qnano(qnano.getQnano())
                .title(title)
                .content(content)
                .rdate(rdate)
                .writer(writer)
                .build();
    }

}
