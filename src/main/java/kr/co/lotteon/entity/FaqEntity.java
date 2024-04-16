package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.dto.FaqDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "csfaq")
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int faqno;

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

    private int hit;

    public String getRdatesub(){
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }

    public FaqDTO toDTO(){
        return FaqDTO.builder()
                .faqno(faqno)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .uid(uid)
                .regip(regip)
                .rdate(rdate)
                //.c1name(Cate1Entity.getC1name())
                //.c2name(Cate2Entity.getC2name())
                .rdatesub(this.getRdatesub())
                .build();
    }
}
