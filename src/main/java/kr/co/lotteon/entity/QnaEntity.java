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
public class QnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnano;

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
    private String file1;
    private String file2;
    private String file3;
    private String file4;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private MemberEntity memberEntity;

    @Column(name = "uid")
 */
    private String uid;

    private String ono;
    private String prodno;
    private int parent;
    private int answercomplete;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    public String getRdatesub(){
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }

    public QnaDTO toDTO(){
        return QnaDTO.builder()
                .qnano(qnano)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .file1(file1)
                .file2(file2)
                .uid(uid)
                .ono(ono)
                .prodno(prodno)
                .parent(parent)
                .answercomplete(answercomplete)
                .regip(regip)
                .rdate(rdate)
                .c1name(cate1Entity.getC1name())
                .c2name(cate2Entity.getC2name())
                .build();
    }
}
