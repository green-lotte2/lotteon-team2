package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.dto.QnaDTO;
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
@Table(name = "qna")

public class CsQna {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnano;

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
    private String file1;
    private String file2;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private User user;
    */

    @Column(name = "uid")
    private String uid;
    private int parent;
    private int answercomplete;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    public String getRdateSub(){
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }


    public QnaDTO toDTO() {
        return QnaDTO.builder()
                .qnano(qnano)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .file1(file1)
                .file2(file2)
                .uid(uid)
                .parent(parent)
                .answercomplete(answercomplete)
                .regip(regip)
                .rdate(rdate)
                .c1name(csCate1.getC1name())
                .c2name(csCate2.getC2name())
                .build();
    }
}
