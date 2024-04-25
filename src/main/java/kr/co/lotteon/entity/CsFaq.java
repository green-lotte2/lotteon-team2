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
@Table(name = "faq")

public class CsFaq {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int faqno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate1", insertable = false, updatable = false)
    private CsCate1 csCate1;

    @Column(name = "cate1")
    private int cate1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "cate1", referencedColumnName = "cate1", insertable = false, updatable = false),
                  @JoinColumn(name = "cate2", referencedColumnName = "cate2", insertable = false, updatable = false)})
    private CsCate2 csCate2;

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

    /*🎈*/
    private int hit;


    public String getRdatesub(){
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }

    public FaqDTO toDTO() {
        return FaqDTO.builder()
                .faqno(faqno)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .uid(uid)
                .regip(regip)
                .rdate(rdate)
                .c1name(csCate1.getC1name())
                .c2name(csCate2.getC2name())
                .rdatesub(this.getRdatesub())
                .hit(hit)
                .build();
    }
}
