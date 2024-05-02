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



    private int qnano;
    private String rcontent;

    @CreationTimestamp
    private LocalDateTime rdate;
    private String writer;

    public ReplyDTO toDTO(){
        return ReplyDTO.builder()
                .replyno(replyno)
                .qnano(qnano)
                .rcontent(rcontent)
                .rdate(rdate)
                .writer(writer)
                .build();
    }

}
