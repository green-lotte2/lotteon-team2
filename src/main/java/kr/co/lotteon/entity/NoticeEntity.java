package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.dto.NoticeDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "csnotice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate1", insertable = false, updatable = false)
    private Cate1Entity cate1Entity;

    @Column(name = "cate1")
    private int cate1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "cate1", referencedColumnName = "cate1", insertable = false, updatable = false),
            @JoinColumn(name = "cate2", referencedColumnName = "cate2", insertable = false, updatable = false)})
    private Cate2Entity cate2Entity;

    @Column(name = "cate2")
    private int cate2;

    private String title;
    private String content;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private MemberEntity memberEntity;

    @Column(name = "uid")
    */
    private String uid;

    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    public String getRdatesub(){
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }

    public NoticeDTO toDTO(){
        return NoticeDTO.builder()
                .noticeno(noticeno)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .uid(uid)
                .regip(regip)
                .rdate(rdate)
                .c1name(cate1Entity.getC1name())
                .c2name(cate2Entity.getC2name())
                .rdatesub(this.getRdatesub())
                .build();
    }
}
