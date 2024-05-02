package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsQna;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    private int qnano;
    private int cate1;
    private int cate2;
    private String title;
    private String content;
    private String file1;
    private String file2;
    private String uid;
    private int parent;
    private int answercomplete;
    private String regip;
    private LocalDateTime rdate;

    private MultipartFile mFile1;



    //추가 필드
    private String writername;
    private String c1name;
    private String c2name;
    private String writermarking;
    private String rdateSub;
    //🎈댓글 컨텐츠
    private String rcontent;

/*
    public String getRdateSub() {
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }
*/
    public CsQna toEntity(){
        return CsQna.builder()
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
                .build();
    }
}
